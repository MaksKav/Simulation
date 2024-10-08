package main.java.maxkavun.simulation.entity;


import main.java.maxkavun.simulation.actions.InitActions;
import main.java.maxkavun.simulation.actions.TurnActions;
import main.java.maxkavun.simulation.entity.herbivore.Herbivore;
import main.java.maxkavun.simulation.entity.herbivore.Pig;
import main.java.maxkavun.simulation.entity.herbivore.Rabbit;
import main.java.maxkavun.simulation.entity.herbivore.resources.HerbivoreResources;
import main.java.maxkavun.simulation.entity.predator.Predator;
import main.java.maxkavun.simulation.entity.predator.Wolf;
import main.java.maxkavun.simulation.map.Cell;
import main.java.maxkavun.simulation.map.Coordinate;
import main.java.maxkavun.simulation.map.SimulationMap;


import java.util.*;


public abstract class Creature extends Entity {

    protected int health;
    protected int availableSteps;
    protected boolean isAlive = true;

    protected Coordinate currentPosition;
    protected List<Coordinate> pathToResources = new ArrayList<>();


    public Creature(Coordinate currentPosition) {
        this.currentPosition = currentPosition;
    }

    public Creature() {
    }

    public abstract void eat(Herbivore herbivore);

    public abstract void eat(HerbivoreResources herbivoreResources);

    public abstract void reloadSteps();


    public void makeMove() {
        int healthAtNow = this.getHealth();
        if (availableSteps == 0) {
            this.reloadSteps();
        }

        executeMovementSteps();


        if (healthAtNow == this.getHealth()){
            this.applyHungerDamage();
            if (this.getHealth() <= 0 ){
                this.creatureDeath();
                TurnActions.putEmptyPlace(this.currentPosition);
            }
        }
    }


    /*
     * This method handles the movement of a creature toward its target,
     * managing its interactions with other entities like herbivores or resources along the way.
     * It moves the creature step by step, allowing it to either consume its target or continue moving if there are steps remaining.
     * */
    private void executeMovementSteps() {
        Coordinate targetCoordinate = this.findClosestResources(SimulationMap.getInstance());

        while (this.availableSteps > 0) {
            this.setPathToResources(findPath(this.currentPosition, targetCoordinate, SimulationMap.getInstance()));
            if (!pathToResources.isEmpty()) {
                Coordinate nextStepCoordinate = this.pathToResources.get(0);

                if (nextStepCoordinate.equals(targetCoordinate)) {
                    Entity targetEntity = SimulationMap.getInstance().getMap().get(targetCoordinate).getEntity();

                    // The logic of interaction between the predator and its resource (herbivore).
                    if (this instanceof Predator) {
                        if (((Herbivore) targetEntity).getHealth() > 0) {
                            this.eat((Herbivore) targetEntity);
                            if (!((Herbivore) targetEntity).getIsAlive() && this.availableSteps > 0) {
                                goNextCell(nextStepCoordinate);
                                if (this.availableSteps > 0 && !((Herbivore) targetEntity).getIsAlive()) {
                                    executeMovementSteps();
                                }
                            } else if (!((Herbivore) targetEntity).getIsAlive() && this.availableSteps <= 0) {
                                SimulationMap.getInstance().getMap().put(nextStepCoordinate, new Cell(nextStepCoordinate, new EmptyPlace()));
                            }
                        }
                        // The logic of interaction between the herbivore and its resource.
                    } else if (this instanceof Herbivore) {
                        if (((HerbivoreResources) targetEntity).getHealth() > 0) {
                            this.eat((HerbivoreResources) targetEntity);
                            if (((HerbivoreResources) targetEntity).getHealth() <= 0 && this.availableSteps > 0) {
                                goNextCell(nextStepCoordinate);
                                if (this.availableSteps > 0) {
                                    executeMovementSteps();
                                }
                            } else if (((HerbivoreResources) targetEntity).getHealth() <= 0 && this.availableSteps <= 0) {
                                SimulationMap.getInstance().getMap().put(nextStepCoordinate, new Cell(nextStepCoordinate, new EmptyPlace()));
                            }
                        }
                    }
                } else {
                    goNextCell(nextStepCoordinate);
                }
            } else {
                /*If there is no path, skips the move*/
                setAvailableSteps(0);
            }
        }
    }


    /*
     * This method searches for the shortest path to the target using a dynamic selection of the next best step.
     * It adds each step to the result until the target is reached.
     */
    public List<Coordinate> findPath(Coordinate start, Coordinate target, SimulationMap map) {

        Queue<Coordinate> queue = new LinkedList<>();
        List<Coordinate> result = new ArrayList<>();
        Set<Coordinate> visitedCoordinates = new HashSet<>();

        queue.add(start);
//        visitedCoordinates.add(start);

        while (!queue.isEmpty()) {
            Coordinate currentCoordinate = queue.poll();
            visitedCoordinates.add(currentCoordinate);


            if (currentCoordinate.equals(target)) {
                result.add(currentCoordinate);
                return result;
            }

            if (!currentCoordinate.equals(start)) {
                result.add(currentCoordinate);
            }


            Optional<Coordinate> bestNeighbour = getNeighbourWithBestDistanceToTarget(currentCoordinate, target, map, visitedCoordinates);
            bestNeighbour.ifPresent(queue::add);
        }
         return Collections.emptyList();
    }


    /*
     * This method selects the most advantageous option for the next step based on the distance to the target.
     * It evaluates the valid neighboring coordinates and returns the one with the minimum distance to the target.
     */
    private Optional<Coordinate> getNeighbourWithBestDistanceToTarget(Coordinate coordinate, Coordinate target, SimulationMap map, Set<Coordinate> visitedCoordinates) {
        List<Coordinate> neighbours = new ArrayList<>();
        int x = coordinate.getX();
        int y = coordinate.getY();

        if (Coordinate.isValidCoordinate(this, new Coordinate(x + 1, y), map) && !visitedCoordinates.contains(new Coordinate(x + 1, y))) {
            neighbours.add(new Coordinate(x + 1, y));
        }
        if (Coordinate.isValidCoordinate(this, new Coordinate(x, y + 1), map) && !visitedCoordinates.contains(new Coordinate(x, y + 1))) {
            neighbours.add(new Coordinate(x, y + 1));
        }
        if (Coordinate.isValidCoordinate(this, new Coordinate(x, y - 1), map) && !visitedCoordinates.contains(new Coordinate(x, y - 1))) {
            neighbours.add(new Coordinate(x, y - 1));
        }
        if (Coordinate.isValidCoordinate(this, new Coordinate(x - 1, y), map) && !visitedCoordinates.contains(new Coordinate(x - 1, y))) {
            neighbours.add(new Coordinate(x - 1, y));
        }


        double minDistance = Double.MAX_VALUE;
        Coordinate coordinateWithMinDistance = null;

        for (Coordinate neighbour : neighbours) {
            double distance = calculateDistance(neighbour, target);
            if (distance < minDistance) {
                minDistance = distance;
                coordinateWithMinDistance = neighbour;
            }
        }
        return Optional.ofNullable(coordinateWithMinDistance);
    }



    /*
     * This method finds the nearest specified object for the selected animal.
     * It returns the currentPosition of the closest resource or null if not found.
     */
    public Coordinate findClosestResources(SimulationMap map) {
        Coordinate animalCoordinate = this.getCurrentPosition();
        Coordinate closestCoordinate = null;
        double minDistance = Double.MAX_VALUE;

        for (Map.Entry<Coordinate, Cell> entry : map.getMap().entrySet()) {
            Coordinate coordinate = entry.getKey();
            Entity cellEntity = entry.getValue().getEntity();

            if (this instanceof Herbivore) {
                if (cellEntity instanceof HerbivoreResources) {
                    double distance = calculateDistance(animalCoordinate, coordinate);
                    if (distance < minDistance) {
                        closestCoordinate = coordinate;
                        minDistance = distance;
                    }
                }
            }
            if (this instanceof Predator) {
                if (cellEntity instanceof Herbivore) {
                    double distance = calculateDistance(animalCoordinate, coordinate);
                    if (distance < minDistance) {
                        closestCoordinate = coordinate;
                        minDistance = distance;
                    }
                }
            }

        }
        if (this instanceof Herbivore && closestCoordinate == null) {
            TurnActions.addHerbivoreResources(3, SimulationMap.getInstance());
            executeMovementSteps();
        }

        if (this instanceof Predator && closestCoordinate == null) {
            TurnActions.checkForVictoryCondition(InitActions.creatures);
        }

        return closestCoordinate;
    }


    /*  This method calculates the distance between two coordinates using the Pythagorean theorem. */
    private double calculateDistance(Coordinate a, Coordinate b) {
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
    }


    private void goNextCell(Coordinate nextStep) {
        Coordinate beforePosition = new Coordinate(this.getCurrentPosition().getX(), this.getCurrentPosition().getY());
        this.setCurrentPosition(nextStep);
        SimulationMap.getInstance().getMap().put(nextStep, new Cell(nextStep, this));
        SimulationMap.getInstance().getMap().put(beforePosition, new Cell(beforePosition, new EmptyPlace()));
        this.setAvailableSteps(this.getAvailableSteps() - 1);
    }


    /* This method decreases the creature's health if it hasn't eaten during the entire round */
    private void applyHungerDamage (){
        if (this instanceof Wolf){
            this.setHealth(this.getHealth() - 12);
        }else if (this instanceof Pig){
            this.setHealth(this.getHealth() - 7);
        } else if (this instanceof Rabbit) {
            this.setHealth(this.getHealth() - 5);
        }
    }


    public boolean getIsAlive() {
        return isAlive;
    }


    public void creatureDeath() {
        isAlive = false;
    }

    public Coordinate getCurrentPosition() {
        return currentPosition;
    }


    public void setPathToResources(List<Coordinate> pathToResources) {
        this.pathToResources = pathToResources;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAvailableSteps() {
        return availableSteps;
    }

    public void setAvailableSteps(int availableSteps) {
        this.availableSteps = availableSteps;
    }

    public void setCurrentPosition(Coordinate currentPosition) {
        this.currentPosition = currentPosition;
    }
}
