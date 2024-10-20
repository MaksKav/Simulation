package main.java.maxkavun.simulation.actions;

import main.java.maxkavun.simulation.display.SimulationDisplay;
import main.java.maxkavun.simulation.entity.*;
import main.java.maxkavun.simulation.entity.herbivore.Pig;
import main.java.maxkavun.simulation.entity.herbivore.Rabbit;
import main.java.maxkavun.simulation.entity.predator.Wolf;
import main.java.maxkavun.simulation.map.Coordinate;
import main.java.maxkavun.simulation.map.SimulationMap;
import main.java.maxkavun.simulation.renderer.ConsoleRenderer;


import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;


public class InitActions {
    public static final List<Creature> creatures = new ArrayList<Creature>();

    // TODO должен только начинать симуляцию
    public static void startSimulation(int moves)  {

        for (int move = 0; move < moves ; move++) {

            InitActions.creatures.removeIf(creature -> !creature.getIsAlive());

            for (Creature creature : InitActions.creatures) {
                if (creature.getIsAlive()) {
                    creature.makeMove();
                }
            }

            ConsoleRenderer.drawMap(SimulationMap.getInstance());
            TurnActions.checkForVictoryCondition(creatures);
            try {
                sleep(1200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        SimulationDisplay.showMiddleDisplay();
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
            TurnActions.addCreature(new Pig());
        }

        if (counterRabbit == 0) {
            TurnActions.addCreature(new Rabbit());
        }

        if (counterWolf == 0) {
           TurnActions.addCreature(new Wolf());
        }
    }
}
