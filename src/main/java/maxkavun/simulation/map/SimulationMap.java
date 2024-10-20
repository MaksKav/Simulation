package main.java.maxkavun.simulation.map;

import main.java.maxkavun.simulation.actions.InitActions;
import main.java.maxkavun.simulation.entity.Entity;

import java.util.HashMap;
import java.util.Map;

// TODO убрать синглтон
public class SimulationMap {
    private final int HEIGHT = 8;
    private final int WIDTH = 80;

    private static SimulationMap instance;
    private final Map<Coordinate, Entity> map;

    private SimulationMap() {
        map = new HashMap<Coordinate , Entity>();
    }

    // TODO initializeMap должен быть отдельно
    public static SimulationMap getInstance() {
        if (instance == null) {
            instance = new SimulationMap();
            InitActions.initializeMap();
        }
        return instance;
    }

    public static void resetInstance() {
        instance = null;
    }

    public Map<Coordinate, Entity> getMap() {
        return map;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public int getWidth() {
        return WIDTH;
    }
}
