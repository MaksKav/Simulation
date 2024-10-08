package main.java.maxkavun.simulation.actions;

import main.java.maxkavun.simulation.display.SimulationDisplay;
import main.java.maxkavun.simulation.entity.Creature;
import main.java.maxkavun.simulation.entity.EmptyPlace;
import main.java.maxkavun.simulation.entity.herbivore.Herbivore;
import main.java.maxkavun.simulation.entity.herbivore.Pig;
import main.java.maxkavun.simulation.entity.herbivore.Rabbit;
import main.java.maxkavun.simulation.entity.herbivore.resources.Apple;
import main.java.maxkavun.simulation.entity.herbivore.resources.Grass;
import main.java.maxkavun.simulation.entity.predator.Predator;
import main.java.maxkavun.simulation.entity.predator.Wolf;
import main.java.maxkavun.simulation.map.Cell;
import main.java.maxkavun.simulation.map.Coordinate;
import main.java.maxkavun.simulation.map.SimulationMap;
import main.java.maxkavun.simulation.renderer.Renderer;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Thread.sleep;
import static main.java.maxkavun.simulation.map.Coordinate.getRandomCoordinate;
import static main.java.maxkavun.simulation.map.Coordinate.isValidCoordinate;


public class TurnActions {


    /*
    This method adds the specified number of resources to the map for herbivores.
    */
    public static void addHerbivoreResources(int quantity, SimulationMap map) {

        for (int i = 0; i < quantity; i++) {
            Coordinate coordinate = Coordinate.getRandomCoordinate();
            if (Coordinate.isValidCoordinate(coordinate, SimulationMap.getInstance())) {
                int random = (int) (Math.random() * 10);
                var cell = new Cell(coordinate, random > 4 ? new Apple() : new Grass());
                map.getMap().put(coordinate, cell);
            } else {
                i--;
            }
        }
    }


    /* Replaces the selected cell with an empty space */
    public static void putEmptyPlace(Coordinate coordinate) {
        SimulationMap.getInstance().getMap().put(coordinate, new Cell(coordinate, new EmptyPlace()));
    }


    public static void checkForVictoryCondition(List<Creature> creatureList) {
        InitActions.creatures.removeIf(creature -> !creature.getIsAlive());
        int herbivoreCount = 0;
        int predatorCount = 0;

        for (Creature creature : creatureList) {
            if (creature instanceof Herbivore) {
                herbivoreCount++;
            } else if (creature instanceof Predator) {
                predatorCount++;
            }
        }

        if (herbivoreCount == 0 && predatorCount > 0) {
            Renderer.drawMap(SimulationMap.getInstance());
            System.out.println("Game over - all herbivores have been eaten");
            System.out.println("Predators WIN ! \n");
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }finally {
                SimulationDisplay.showFinalDisplay();
            }
        } else if (predatorCount == 0 && herbivoreCount > 0) {
            Renderer.drawMap(SimulationMap.getInstance());
            System.out.println("Game over - all predators died");
            System.out.println("Herbivores WIN ! \n");
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }finally {
                SimulationDisplay.showFinalDisplay();
            }
        }
    }


    public static void addAnimals(Scanner scanner, String creature_type) {
        Random random = new Random();
        while (true) {
            try {
                int count = scanner.nextInt();
                if (count < 1 || count > 3) {
                    System.out.println("You can only add between 1 and 3 ");
                    continue;
                }

                for (int i = 0; i < count; i++) {
                    Coordinate coordinate = Coordinate.getRandomCoordinate();
                    if (Coordinate.isValidCoordinate(coordinate, SimulationMap.getInstance())) {
                        switch (creature_type) {
                            case "all types":
                                int rand = random.nextInt(3);
                                switch (rand) {
                                    case 0:
                                        addCreature(new Wolf());
                                        break;
                                    case 1:
                                        addCreature(new Rabbit());
                                        break;
                                    case 2:
                                        addCreature(new Pig());
                                }
                                break;
                            case "herbivores":
                                int rand1 = random.nextInt(2);
                                addCreature(rand1 > 0 ? new Pig() : new Rabbit());
                                break;
                            case "predators":
                                addCreature(new Wolf());
                                break;
                        }
                    } else {
                        i--;
                    }
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please enter a number.");
                scanner.next(); //
            }
        }
    }


    public static void addCreature(Creature creature) {
        Coordinate coordinate = getRandomCoordinate();

        if (isValidCoordinate(coordinate, SimulationMap.getInstance())) {
            creature.setCurrentPosition(coordinate);
            Cell cell = new Cell(coordinate, creature);
            SimulationMap.getInstance().getMap().put(coordinate, cell);
            InitActions.creatures.add((Creature) cell.getEntity());
        } else {
            addCreature(creature);
        }
    }

}
