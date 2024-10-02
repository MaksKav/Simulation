package main.java.maxkavun.simulation.map;

import main.java.maxkavun.simulation.entity.Barrier;
import main.java.maxkavun.simulation.entity.Creature;
import main.java.maxkavun.simulation.entity.EmptyPlace;
import main.java.maxkavun.simulation.entity.Entity;

import java.util.Objects;

public class Coordinate {

    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /*
     * This method checks whether the given currentPosition is valid within the map boundaries
     * and whether it does not contain an entity that blocks movement.
     */
    public static boolean isValidCoordinate(Coordinate coordinate, SimulationMap map) {
        int x = coordinate.getX();
        int y = coordinate.getY();
        Entity cellEntity = map.getMap().get(coordinate).getEntity();

        return cellEntity instanceof EmptyPlace;
    }

    public static Coordinate getRandomCoordinate() {
        int randomHeight = (int) (Math.random() * (SimulationMap.getInstance().getHEIGHT()));
        int randomWidth = (int) (Math.random() * (SimulationMap.getInstance().getWIDTH()));
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
}
