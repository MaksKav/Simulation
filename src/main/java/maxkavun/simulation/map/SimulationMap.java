package main.java.maxkavun.simulation.map;

import main.java.maxkavun.simulation.actions.InitActions;

import java.util.HashMap;
import java.util.Map;



public class SimulationMap {
    private final int HEIGHT = 8;
    private final int WIDTH = 80;

    private static SimulationMap instance;
    private final Map<Coordinate, Cell> map;


    private SimulationMap() {
        map = new HashMap<Coordinate , Cell>();
    }


    public static SimulationMap getInstance() {
        if (instance == null) {
            instance = new SimulationMap();
            InitActions.initialMap();
        }
        return instance;
    }

    public static void resetInstance() {
        instance = null;
    }


    public Map<Coordinate, Cell> getMap() {
        return map;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public int getWIDTH() {
        return WIDTH;
    }
}
