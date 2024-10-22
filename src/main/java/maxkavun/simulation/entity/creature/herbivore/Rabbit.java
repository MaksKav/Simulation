package main.java.maxkavun.simulation.entity.creature.herbivore;

import main.java.maxkavun.simulation.map.Coordinate;

public class Rabbit extends Herbivore {

    private static final int RABBIT_HUNGER_DAMAGE = 5;

    public Rabbit(Coordinate coordinate) {
        super(coordinate);
        this.icon = "\uD83D\uDC30" ;
        this.health = 130 ;
        this.availableSteps = 3 ;
    }

    public Rabbit() {
        this.icon = "\uD83D\uDC30" ;
        this.health = 130 ;
        this.availableSteps = 3 ;
    }

    @Override
    public void reloadSteps() {
        setAvailableSteps(3);
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
