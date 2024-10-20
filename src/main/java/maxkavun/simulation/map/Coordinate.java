package main.java.maxkavun.simulation.map;


import main.java.maxkavun.simulation.entity.Creature;
import main.java.maxkavun.simulation.entity.EmptyPlace;
import main.java.maxkavun.simulation.entity.Entity;
import main.java.maxkavun.simulation.entity.herbivore.Herbivore;
import main.java.maxkavun.simulation.entity.herbivore.resources.HerbivoreResources;
import main.java.maxkavun.simulation.entity.predator.Predator;

import java.util.Objects;

public class Coordinate {

    private int x;
    private int y;


    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }


    /*
      This method checks if the place is empty.
     */
    public static boolean isValidCoordinate(Coordinate coordinate, SimulationMap map) {
        if (map.getMap().containsKey(coordinate)) {
            Entity cellEntity = map.getMap().get(coordinate).getEntity();
            return cellEntity instanceof EmptyPlace;
        }
        return false;
    }

    
    /*
     This method checks if the given coordinate is valid for the creature's next move.
     */
    public static boolean isValidCoordinate(Creature creature, Coordinate coordinate, SimulationMap map) {
        Cell cell = map.getMap().get(coordinate);
        if (cell != null) {
            Entity cellEntity = cell.getEntity();
            if (creature instanceof Herbivore) {
                return cellEntity instanceof HerbivoreResources || cellEntity instanceof EmptyPlace;
            } else if (creature instanceof Predator) {
                return cellEntity instanceof Herbivore || cellEntity instanceof EmptyPlace;
            }
        }
        return false;
    }


    public static Coordinate getRandomCoordinate() {
        int randomHeight = (int) (Math.random() * (SimulationMap.getInstance().getHeight()));
        int randomWidth = (int) (Math.random() * (SimulationMap.getInstance().getWidth()));
        return new Coordinate(randomWidth, randomHeight);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

