package main.java.maxkavun.simulation.renderer;

import main.java.maxkavun.simulation.map.Cell;
import main.java.maxkavun.simulation.map.Coordinate;
import main.java.maxkavun.simulation.map.SimulationMap;

import java.util.Map;

public class Renderer {

    public static void drawMap(SimulationMap map) {
        Cell[][] cellArray = new Cell[map.getHEIGHT()][map.getWIDTH()];
        for (Map.Entry<Coordinate, Cell> entry : SimulationMap.getInstance().getMap().entrySet()) {
            cellArray[entry.getKey().getX()][entry.getKey().getY()] = entry.getValue();
        }

        System.out.println();
        for (Cell[] cells : cellArray) {
            for (Cell cell : cells) {
                System.out.print(cell);
            }
            System.out.println();
        }
        System.out.println();
    }
}
