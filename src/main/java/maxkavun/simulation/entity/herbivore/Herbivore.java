package main.java.maxkavun.simulation.entity.herbivore;

import main.java.maxkavun.simulation.entity.Creature;
import main.java.maxkavun.simulation.entity.herbivore.resources.HerbivoreResources;
import main.java.maxkavun.simulation.map.Coordinate;

public abstract class Herbivore extends Creature {


    public Herbivore(Coordinate currentPosition) {
        super(currentPosition);
    }

    public Herbivore() {
    }

    public void eat(HerbivoreResources herbivoreResources) {
        if (this.getAvailableSteps() > 0) {
            this.setAvailableSteps(this.getAvailableSteps() - 1);
            this.setHealth(this.getHealth() + 20);
            herbivoreResources.setHealth(herbivoreResources.getHealth() - 1);
        }
    }


    @Override
    public void eat(Herbivore herbivore) {
        throw new UnsupportedOperationException("Herbivores cannot eat other herbivores.");
    }
}
