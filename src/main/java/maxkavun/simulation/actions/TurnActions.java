package main.java.maxkavun.simulation.actions;

import main.java.maxkavun.simulation.entity.herbivore.resources.Apple;
import main.java.maxkavun.simulation.entity.herbivore.resources.Grass;
import main.java.maxkavun.simulation.map.Cell;
import main.java.maxkavun.simulation.map.Coordinate;
import main.java.maxkavun.simulation.map.SimulationMap;
import java.util.concurrent.locks.ReentrantLock;

public class TurnActions {
    private static final ReentrantLock reentrantLock = new ReentrantLock();


    /*
    This method adds the specified number of resources to the map for herbivores.
    */
    //TODO использовать этот метод для того что бы дать возможность клиенту создать n-нное количество ресурсов на карте (как доп.функционал )
    public static void addHerbivoreResources(int quantity, SimulationMap map) {
        try {
            reentrantLock.lock();

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
        }finally {
            reentrantLock.unlock();
        }
    }
}
