package main.java.maxkavun.simulation.map;

import main.java.maxkavun.simulation.entity.EmptyPlace;
import main.java.maxkavun.simulation.entity.Entity;
import main.java.maxkavun.simulation.entity.creature.Creature;
import main.java.maxkavun.simulation.entity.creature.herbivore.Herbivore;
import main.java.maxkavun.simulation.entity.creature.herbivore.resources.HerbivoreResources;
import main.java.maxkavun.simulation.entity.creature.predator.Predator;

public class SimulationMapUtils {

    /*
     This method checks if the place is empty.
     */
    public static boolean isValidCoordinateOnMap(Coordinate coordinate, SimulationMap map) {
        if (map.getMap().containsKey(coordinate)) {
            Entity cellEntity = map.getMap().get(coordinate);
            return cellEntity instanceof EmptyPlace;
        }
        return false;
    }


    /*
     This method checks if the given coordinate is valid for the creature's next move.
     */
    public static boolean isValidCoordinateOnMap(Creature creature, Coordinate coordinate, SimulationMap map) {
        Entity cellEntity = map.getMap().get(coordinate);
        if (cellEntity != null) {
            if (creature instanceof Herbivore) {
                return cellEntity instanceof HerbivoreResources || cellEntity instanceof EmptyPlace;
            } else if (creature instanceof Predator) {
                return cellEntity instanceof Herbivore || cellEntity instanceof EmptyPlace;
            }
        }
        return false;
    }


    public static Coordinate getRandomCoordinateOnMap() {
        int randomHeight = (int) (Math.random() * (SimulationMap.getInstance().getHeight()));
        int randomWidth = (int) (Math.random() * (SimulationMap.getInstance().getWidth()));
        return new Coordinate(randomWidth, randomHeight);
    }


}
