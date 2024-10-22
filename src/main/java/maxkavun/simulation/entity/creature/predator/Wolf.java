package main.java.maxkavun.simulation.entity.creature.predator;

import main.java.maxkavun.simulation.map.Coordinate;

public class Wolf extends Predator {

    private static final int RABBIT_HUNGER_DAMAGE = 12;

    public Wolf(Coordinate coordinate) {
        super(coordinate);
        this.icon = "\uD83D\uDC3A";
        this.damage = 100;
        this.health = 200;
        this.availableSteps = 2;
    }

    public Wolf() {
        this.icon = "\uD83D\uDC3A";
        this.damage = 100;
        this.health = 200;
        this.availableSteps = 2;
    }

    @Override
    public void reloadSteps() {
        setAvailableSteps(2);
    }

    @Override
    public int getHungerDamage() {
        return RABBIT_HUNGER_DAMAGE;
    }

    @Override
    public String toString() {
        return icon;
    }

}
