package main.java.maxkavun.simulation.actions;

import main.java.maxkavun.simulation.display.SimulationDisplay;
import main.java.maxkavun.simulation.entity.*;
import main.java.maxkavun.simulation.entity.creature.Creature;
import main.java.maxkavun.simulation.entity.creature.herbivore.Pig;
import main.java.maxkavun.simulation.entity.creature.herbivore.Rabbit;
import main.java.maxkavun.simulation.entity.creature.predator.Wolf;
import main.java.maxkavun.simulation.map.Coordinate;
import main.java.maxkavun.simulation.map.SimulationMap;
import main.java.maxkavun.simulation.renderer.Renderer;


import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;


public class InitActionService {
    public static final List<Creature> creatures = new ArrayList<Creature>();


    public static void startSimulation(int moves , Renderer renderer)  {

        for (int move = 0; move < moves ; move++) {

            InitActionService.creatures.removeIf(creature -> !creature.getIsAlive());

            for (Creature creature : InitActionService.creatures) {
                if (creature.getIsAlive()) {
                    creature.makeMove();
                }
            }

            renderer.drawMap(SimulationMap.getInstance());
            TurnActionService.checkForVictoryCondition(creatures);
            try {
                sleep(1200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        SimulationDisplay.displaySimulationOptions(renderer);
    }

    /* Initializes the map and adds all created creatures to the list for further monitoring of their alive status */
    public static void initializeMap() {
        int counterRabbit = 0;
        int counterWolf = 0;
        int counterPig = 0;

        EntityFactory entityFactory = new EntityFactory();

        for (int i = 0; i < SimulationMap.getInstance().getHeight(); i++) {
            for (int j = 0; j < SimulationMap.getInstance().getWidth(); j++) {
                Coordinate coordinate = new Coordinate(i, j);
                Entity entity = entityFactory.createEntity(coordinate);

                if (entity instanceof Creature) {
                    creatures.add((Creature) entity);
                    ((Creature) entity).setCurrentPosition(coordinate);
                    if (entity instanceof Pig) {
                        counterPig++;
                    } else if (entity instanceof Rabbit) {
                        counterRabbit++;
                    } else if (entity instanceof Wolf) {
                        counterWolf++;
                    }
                }

                SimulationMap.getInstance()
                        .getMap()
                        .put(coordinate, entity);
            }
        }
        if (counterPig == 0) {
            TurnActionService.spawnCreatureOnValidPosition(new Pig());
        }

        if (counterRabbit == 0) {
            TurnActionService.spawnCreatureOnValidPosition(new Rabbit());
        }

        if (counterWolf == 0) {
           TurnActionService.spawnCreatureOnValidPosition(new Wolf());
        }
    }
}
