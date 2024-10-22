package main.java.maxkavun.simulation.actions;

import main.java.maxkavun.simulation.display.SimulationDisplay;
import main.java.maxkavun.simulation.entity.creature.Creature;
import main.java.maxkavun.simulation.entity.EmptyPlace;
import main.java.maxkavun.simulation.entity.creature.herbivore.Herbivore;
import main.java.maxkavun.simulation.entity.creature.herbivore.Pig;
import main.java.maxkavun.simulation.entity.creature.herbivore.Rabbit;
import main.java.maxkavun.simulation.entity.creature.herbivore.resources.Apple;
import main.java.maxkavun.simulation.entity.creature.herbivore.resources.Grass;
import main.java.maxkavun.simulation.entity.creature.herbivore.resources.HerbivoreResources;
import main.java.maxkavun.simulation.entity.creature.predator.Predator;
import main.java.maxkavun.simulation.entity.creature.predator.Wolf;
import main.java.maxkavun.simulation.map.Coordinate;
import main.java.maxkavun.simulation.map.SimulationMap;
import main.java.maxkavun.simulation.map.SimulationMapUtils;
import main.java.maxkavun.simulation.renderer.ConsoleRenderer;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Thread.sleep;



public class TurnActionService {

    /*
    This method adds the specified number of resources to the map for herbivores.
    */
    public static void addHerbivoreResources(int quantity, SimulationMap map) {
        final int APPLE_THRESHOLD = 4; // Threshold for generating an apple
        final int MAX_RANDOM = 10; // Maximum value for random number generation

        for (int i = 0; i < quantity; i++) {
            Coordinate coordinate = SimulationMapUtils.getRandomCoordinateOnMap();
            if (SimulationMapUtils.isValidCoordinateOnMap(coordinate, map)) {
                int random = (int) (Math.random() * MAX_RANDOM);
                HerbivoreResources resource = random > APPLE_THRESHOLD ? new Apple() : new Grass();
                map.getMap().put(coordinate, resource); // Directly put the resource in the map
            } else {
                i--;
            }
        }
    }


    /* Replaces the selected cell with an empty space */
    public static void putEmptyPlaceOnMap(Coordinate coordinate) {
        SimulationMap.getInstance().getMap().put(coordinate, new EmptyPlace());
    }


    public static void checkForVictoryCondition(List<Creature> creatureList) {
        ConsoleRenderer renderer = new ConsoleRenderer();

        InitActionService.creatures.removeIf(creature -> !creature.getIsAlive());
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
            renderer.drawMap(SimulationMap.getInstance());
            System.out.println("Game over - all herbivores have been eaten");
            System.out.println("Predators WIN ! \n");
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }finally {
                SimulationDisplay.showEndSimulationMenu(renderer);
            }
        } else if (predatorCount == 0 && herbivoreCount > 0) {
            renderer.drawMap(SimulationMap.getInstance());
            System.out.println("Game over - all predators died");
            System.out.println("Herbivores WIN ! \n");
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }finally {
                SimulationDisplay.showEndSimulationMenu(renderer);
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
                    Coordinate coordinate = SimulationMapUtils.getRandomCoordinateOnMap();
                    if (SimulationMapUtils.isValidCoordinateOnMap(coordinate, SimulationMap.getInstance())) {
                        switch (creature_type) {
                            case "all types":
                                int rand = random.nextInt(3);
                                switch (rand) {
                                    case 0:
                                        spawnCreatureOnValidPosition(new Wolf());
                                        break;
                                    case 1:
                                        spawnCreatureOnValidPosition(new Rabbit());
                                        break;
                                    case 2:
                                        spawnCreatureOnValidPosition(new Pig());
                                }
                                break;
                            case "herbivores":
                                int rand1 = random.nextInt(2);
                                spawnCreatureOnValidPosition(rand1 > 0 ? new Pig() : new Rabbit());
                                break;
                            case "predators":
                                spawnCreatureOnValidPosition(new Wolf());
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


    public static void spawnCreatureOnValidPosition(Creature creature) {
        Coordinate coordinate = SimulationMapUtils.getRandomCoordinateOnMap();

        if (SimulationMapUtils.isValidCoordinateOnMap(coordinate, SimulationMap.getInstance())) {
            creature.setCurrentPosition(coordinate);
            SimulationMap.getInstance().getMap().put(coordinate, creature);
            InitActionService.creatures.add((Creature) creature);
        } else {
            spawnCreatureOnValidPosition(creature);
        }
    }

}
