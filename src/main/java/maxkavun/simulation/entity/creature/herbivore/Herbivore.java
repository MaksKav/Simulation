package main.java.maxkavun.simulation.entity.creature.herbivore;

import main.java.maxkavun.simulation.entity.creature.Creature;
import main.java.maxkavun.simulation.entity.creature.herbivore.resources.HerbivoreResources;
import main.java.maxkavun.simulation.map.Coordinate;

public abstract class Herbivore extends Creature {
    private static final int HEALTH_GAIN_FROM_FOOD = 20;
    private static final int RESOURCE_HEALTH_DECREMENT = 1;
    private static final int ONE_STEP = 1;

    public Herbivore(Coordinate currentPosition) {
        super(currentPosition);
    }

    public Herbivore() {
    }

    public void eat(HerbivoreResources herbivoreResources) {
        if (this.getAvailableSteps() > 0) {
            this.setAvailableSteps(this.getAvailableSteps() - ONE_STEP);
            this.setHealth(this.getHealth() + HEALTH_GAIN_FROM_FOOD);
            herbivoreResources.setHealth(herbivoreResources.getHealth() - RESOURCE_HEALTH_DECREMENT);
        }
    }


    @Override
    public void eat(Herbivore herbivore) {
        throw new UnsupportedOperationException("Herbivores cannot eat other herbivores.");
    }
}
