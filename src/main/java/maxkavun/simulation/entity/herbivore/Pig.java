package main.java.maxkavun.simulation.entity.herbivore;

import main.java.maxkavun.simulation.map.Coordinate;

public class Pig extends Herbivore {


    public Pig(Coordinate coordinate) {
        super(coordinate);
        this.icon = "\uD83D\uDC37" ;
        this.health = 360;
        this.availableSteps = 1;
    }

    public Pig() {
        this.icon = "\uD83D\uDC37" ;
        this.health = 360;
        this.availableSteps = 1;
    }

    @Override
    public void reloadSteps() {
        setAvailableSteps(1);
    }

    @Override
    public String toString() {
        return icon;
    }
}
