package main.java.maxkavun.simulation.entity;

import main.java.maxkavun.simulation.actions.InitActions;
import main.java.maxkavun.simulation.actions.TurnActions;
import main.java.maxkavun.simulation.entity.herbivore.Herbivore;
import main.java.maxkavun.simulation.entity.herbivore.resources.HerbivoreResources;
import main.java.maxkavun.simulation.entity.predator.Predator;
import main.java.maxkavun.simulation.map.Cell;
import main.java.maxkavun.simulation.map.Coordinate;
import main.java.maxkavun.simulation.map.SimulationMap;

import java.util.*;


public abstract class Creature extends Entity implements Runnable {

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


    @Override
    public void run() {
        while (getIsAlive()) {
            try {
                synchronized (this) {
                    wait();
                }
                makeMove();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }


    public void makeMove() {
        synchronized (SimulationMap.getInstance()) {
            for (int i = 0; i < this.availableSteps; i++) {
                Coordinate targetCoordinate = this.findClosestResources(SimulationMap.getInstance());

                if (targetCoordinate != null) {
                    pathToResources = findPath(this.currentPosition, targetCoordinate, SimulationMap.getInstance());
                    Coordinate nextStep = pathToResources.get(0);

                    if (nextStep.equals(targetCoordinate)) {
                        Entity targetEntity = SimulationMap.getInstance().getMap().get(targetCoordinate).getEntity();

                        if (targetEntity instanceof Herbivore) {
                            if (((Herbivore) targetEntity).isAlive()) {
                                this.eat((Herbivore) targetEntity);
                                if (!((Herbivore) targetEntity).isAlive() && this.availableSteps > 0) {
                                    goNextCell(nextStep);
                                }
                            }
                        } else if (targetEntity instanceof HerbivoreResources) {
                            if (((HerbivoreResources) targetEntity).getHealth() > 0) {
                                this.eat((HerbivoreResources) targetEntity);
                                if (((HerbivoreResources) targetEntity).getHealth() <= 0 && this.availableSteps > 0) {
                                    goNextCell(nextStep);
                                }
                            }
                        }
                    } else {
                        goNextCell(nextStep);
                    }
                } else {
                    throw new NullPointerException("Target coordinate is null");
                }

            }
        }
    }


    /*
     * This method searches for the shortest path to the target using a dynamic selection of the next best step.
     * It adds each step to the result until the target is reached.
     */
    public static List<Coordinate> findPath(Coordinate start, Coordinate target, SimulationMap map) {
        Queue<Coordinate> queue = new LinkedList<Coordinate>();
        List<Coordinate> result = new ArrayList<>();

        queue.add(start);

        while (!queue.isEmpty() && (!queue.contains(null))) {
            Coordinate currentCoordinate = queue.poll();
            result.add(currentCoordinate);

            if (currentCoordinate.equals(target)) {
                queue.clear();
            }

            queue.add(getNeighboursWithBestDistance(currentCoordinate, target, map));
        }
        return result;
    }

    /*
     * This method selects the most advantageous option for the next step based on the distance to the target.
     * It evaluates the valid neighboring coordinates and returns the one with the minimum distance to the target.
     */
    private static Coordinate getNeighboursWithBestDistance(Coordinate coordinate, Coordinate target, SimulationMap map) {
        List<Coordinate> neighbours = new ArrayList<>();
        Coordinate coordinateWithMinDistance = null;
        int x = coordinate.getX();
        int y = coordinate.getY();

        if (Coordinate.isValidCoordinate(new Coordinate(x + 1, y), map)) {
            neighbours.add(new Coordinate(x + 1, y));
        }
        if (Coordinate.isValidCoordinate(new Coordinate(x, y + 1), map)) {
            neighbours.add(new Coordinate(x, y + 1));
        }
        if (Coordinate.isValidCoordinate(new Coordinate(x, y - 1), map)) {
            neighbours.add(new Coordinate(x, y - 1));
        }
        if (Coordinate.isValidCoordinate(new Coordinate(x - 1, y), map)) {
            neighbours.add(new Coordinate(x - 1, y));
        }

        double minDistance = Double.MAX_VALUE;
        for (int i = 0; i < neighbours.size(); i++) {
            double distance = calculateDistance(neighbours.get(i), target);
            if (distance < minDistance) {
                minDistance = distance;
                coordinateWithMinDistance = neighbours.get(i);
            }
        }
        return coordinateWithMinDistance;
    }

    /*
     * This method checks the neighboring coordinates around the current currentPosition.
     * It returns a list of these neighbors.
     */
    private static List<Coordinate> checkedCoordinates(Coordinate coordinate, SimulationMap map) {
        List<Coordinate> neighbours = new ArrayList<>();
        int x = coordinate.getX();
        int y = coordinate.getY();

        neighbours.add(new Coordinate(x + 1, y));
        neighbours.add(new Coordinate(x, y + 1));
        neighbours.add(new Coordinate(x - 1, y));
        neighbours.add(new Coordinate(x, y - 1));

        return neighbours;
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
            TurnActions.addHerbivoreResources(1, SimulationMap.getInstance().getMap());
        }
        if (this instanceof Predator && closestCoordinate == null) {
            System.out.println("Game over - all herbivores have been eaten");
            System.exit(1);
        }
        return closestCoordinate;
    }

    /*
     * This method calculates the distance between two coordinates using the Pythagorean theorem.
     */
    private static double calculateDistance(Coordinate a, Coordinate b) {
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
    }

    private void goNextCell(Coordinate nextStep) {
        Coordinate beforePosition = new Coordinate(this.getCurrentPosition().getX(), this.getCurrentPosition().getY());
        this.setCurrentPosition(nextStep);
        SimulationMap.getInstance().getMap().put(nextStep, new Cell(nextStep, this));
        SimulationMap.getInstance().getMap().put(beforePosition, new Cell(beforePosition, new EmptyPlace()));
        this.setAvailableSteps(this.getAvailableSteps() - 1);
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    public void creatureDeath() {
        isAlive = false;
        InitActions.creatures.remove(this);
    }

    public Coordinate getCurrentPosition() {
        return currentPosition;
    }

    public List<Coordinate> getPathToResources() {
        return pathToResources;
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
