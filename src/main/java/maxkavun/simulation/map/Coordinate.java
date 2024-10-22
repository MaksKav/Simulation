package main.java.maxkavun.simulation.map;


import main.java.maxkavun.simulation.entity.creature.Creature;
import main.java.maxkavun.simulation.entity.EmptyPlace;
import main.java.maxkavun.simulation.entity.Entity;
import main.java.maxkavun.simulation.entity.creature.herbivore.Herbivore;
import main.java.maxkavun.simulation.entity.creature.herbivore.resources.HerbivoreResources;
import main.java.maxkavun.simulation.entity.creature.predator.Predator;

import java.util.Objects;

public class Coordinate {

    private int x;
    private int y;


    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
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

