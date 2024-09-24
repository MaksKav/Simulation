package main.java.maxkavun.simulation.map;

import main.java.maxkavun.simulation.entity.*;

import java.util.HashMap;
import java.util.Map;

public class SimulationMap {
    private final int HEIGHT = 8;
    private final int WIDTH = 120;

    private static SimulationMap instance;
    private Map<Coordinate, Entity> map;


    private SimulationMap() {
        map = new HashMap<Coordinate, Entity>();
        initialMap();
    }

    public static SimulationMap getInstance() {
        if (instance == null) {
            instance = new SimulationMap();
        }
        return instance;
    }


    private void initialMap() {
        int counterPig = 0;
        int counterRabbit = 0;
        int counterWolf = 0;

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                Coordinate coordinate = new Coordinate(i, j);
                int randomInt = (int) (Math.random() * 17);
                switch (randomInt) {
                    case 0:
                    case 1:
                        map.put(coordinate, new Rock());
                        break;
                    case 2:
                        map.put(coordinate, new Pig());
                        counterPig++;
                        break;
                    case 3:
                        map.put(coordinate, new Rabbit());
                        counterRabbit++;
                        break;
                    case 4:
                        map.put(coordinate, new Wolf());
                        counterWolf++;
                        break;
                    case 5:
                    case 6:
                        map.put(coordinate, new Tree());
                        break;
                    default:
                        map.put(coordinate, new EmptyPlace());
                }
            }
        }
        if (counterPig == 0) {
            int randomHeight = (int) (Math.random() * 9);
            int randomWidth = (int) (Math.random() * 121);
            Coordinate coordinate = new Coordinate(randomWidth, randomHeight);
            map.put(coordinate, new Pig());
        }

        if (counterRabbit == 0) {
            int randomHeight = (int) (Math.random() * 9);
            int randomWidth = (int) (Math.random() * 121);
            Coordinate coordinate = new Coordinate(randomWidth, randomHeight);
            map.put(coordinate, new Rabbit());
        }

        if (counterWolf == 0) {
            int randomHeight = (int) (Math.random() * (HEIGHT + 1));
            int randomWidth = (int) (Math.random() * (WIDTH + 1));
            Coordinate coordinate = new Coordinate(randomWidth, randomHeight);
            map.put(coordinate, new Wolf());
        }
    }

    public void drawMap() {

    }
}
