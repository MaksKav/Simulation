package main.java.maxkavun.simulation.actions;

import main.java.maxkavun.simulation.display.SimulationDisplay;
import main.java.maxkavun.simulation.entity.Creature;
import main.java.maxkavun.simulation.entity.EmptyPlace;
import main.java.maxkavun.simulation.entity.Rock;
import main.java.maxkavun.simulation.entity.Tree;
import main.java.maxkavun.simulation.entity.herbivore.Pig;
import main.java.maxkavun.simulation.entity.herbivore.Rabbit;
import main.java.maxkavun.simulation.entity.herbivore.resources.Apple;
import main.java.maxkavun.simulation.entity.herbivore.resources.Grass;
import main.java.maxkavun.simulation.entity.predator.Wolf;
import main.java.maxkavun.simulation.map.Cell;
import main.java.maxkavun.simulation.map.Coordinate;
import main.java.maxkavun.simulation.map.SimulationMap;
import main.java.maxkavun.simulation.renderer.Renderer;


import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;


public class InitActions {
    public static final List<Creature> creatures = new ArrayList<Creature>();


    public static void startSimulation(int moves)  {

        for (int move = 0; move < moves ; move++) {

            InitActions.creatures.removeIf(creature -> !creature.getIsAlive());

            for (Creature creature : InitActions.creatures) {
                if (creature.getIsAlive()) {
                    creature.makeMove();
                }
            }

            Renderer.drawMap(SimulationMap.getInstance());
            TurnActions.checkForVictoryCondition(creatures);
            try {
                sleep(1200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        SimulationDisplay.showMiddleDisplay();
    }

    public static void initialMap() {
        int counterRabbit = 0;
        int counterWolf = 0;
        int counterPig = 0;

        for (int i = 0; i < SimulationMap.getInstance().getHEIGHT(); i++) {
            for (int j = 0; j < SimulationMap.getInstance().getWIDTH(); j++) {
                Coordinate coordinate = new Coordinate(i, j);
                int randomInt = (int) (Math.random() * 201);
                Cell cell;

                switch (randomInt) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                        cell = new Cell(coordinate, new Rock());
                        break;
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                        cell = new Cell(coordinate, new Tree());
                        break;
                    case 9:
                        cell = new Cell(coordinate, new Pig(coordinate));
                        creatures.add((Creature) cell.getEntity());
                        counterPig++;
                        break;
                    case 10:
                        cell = new Cell(coordinate, new Rabbit(coordinate));
                        creatures.add((Creature) cell.getEntity());
                        counterRabbit++;
                        break;
                    case 11:
                        cell = new Cell(coordinate, new Wolf(coordinate));
                        creatures.add((Creature) cell.getEntity());
                        counterWolf++;
                        break;
                    case 12:
                    case 13:
                    case 14:
                        cell = new Cell(coordinate, new Grass());
                        break;
                    case 15:
                    case 16:
                    case 17:
                        cell = new Cell(coordinate, new Apple());
                        break;
                    default:
                        cell = new Cell(coordinate, new EmptyPlace());
                }
                SimulationMap.getInstance().getMap().put(coordinate, cell);
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
