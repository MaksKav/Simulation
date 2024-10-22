package main.java.maxkavun.simulation.renderer;

import main.java.maxkavun.simulation.entity.Entity;
import main.java.maxkavun.simulation.map.Coordinate;
import main.java.maxkavun.simulation.map.SimulationMap;

import java.util.Map;

public class ConsoleRenderer implements Renderer {


    @Override
    public void drawMap(SimulationMap map) {
        Entity[][] cellArray = new Entity[map.getHeight()][map.getWidth()];
        for (Map.Entry<Coordinate, Entity> entry : SimulationMap.getInstance().getMap().entrySet()) {
            cellArray[entry.getKey().getX()][entry.getKey().getY()] = entry.getValue();
        }

        System.out.println();
        for (Entity[] entities : cellArray) {
            for (Entity entity : entities) {
                System.out.print(entity);
            }
            System.out.println();
        }
        System.out.println();
    }
}
