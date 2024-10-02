package main.java.maxkavun.simulation.entity.predator;

import main.java.maxkavun.simulation.entity.Creature;
import main.java.maxkavun.simulation.entity.herbivore.Herbivore;
import main.java.maxkavun.simulation.entity.herbivore.resources.HerbivoreResources;
import main.java.maxkavun.simulation.map.Coordinate;

public abstract class Predator extends Creature {

    protected int damage;

    public Predator(Coordinate currentPosition) {
        super(currentPosition);
    }

    public Predator() {
    }

    public void eat(Herbivore herbivore) {
        if (this.availableSteps > 0 ){
            this.setAvailableSteps(this.getAvailableSteps()-1);
            herbivore.setHealth(herbivore.getHealth()-this.damage);
            this.setHealth(this.getHealth() + (this.damage/2 ) );
            if (herbivore.getHealth() < 0 ){
                herbivore.creatureDeath();
            }
        }
    }


    @Override
    public void eat(HerbivoreResources herbivoreResources) {
        throw new UnsupportedOperationException("Predators cannot eat resources.");
    }



}
