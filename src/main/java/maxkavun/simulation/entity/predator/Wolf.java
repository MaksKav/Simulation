package main.java.maxkavun.simulation.entity.predator;

import main.java.maxkavun.simulation.map.Coordinate;

public class Wolf extends Predator {


    public Wolf(Coordinate coordinate) {
        super(coordinate);
        this.icon = "\uD83D\uDC3A";
        this.damage = 100;
        this.availableSteps = 2;
    }

    public Wolf() {
        this.icon = "\uD83D\uDC3A";
        this.damage = 100;
        this.availableSteps = 2;
    }

    @Override
    public void reloadSteps() {
        setAvailableSteps(2);
    }

    @Override
    public String toString() {
        return icon;
    }

}
