package main.java.maxkavun.simulation.entity.creature.predator;

import main.java.maxkavun.simulation.entity.creature.Creature;
import main.java.maxkavun.simulation.entity.creature.herbivore.Herbivore;
import main.java.maxkavun.simulation.entity.creature.herbivore.resources.HerbivoreResources;
import main.java.maxkavun.simulation.map.Coordinate;

public abstract class Predator extends Creature {

    protected int damage;
    protected int health;

    public Predator(Coordinate currentPosition) {
        super(currentPosition);
    }

    public Predator() {
    }

    public void eat(Herbivore herbivore) {
        if (this.availableSteps > 0 ){
            this.setAvailableSteps(this.getAvailableSteps()-1);
            herbivore.setHealth(herbivore.getHealth()-this.getDamage());
            this.setHealth(this.getHealth() + (this.getDamage()/2 ) );
            if (herbivore.getHealth() <= 0 ){
                herbivore.creatureDeath();
            }
        }
    }


    @Override
    public void eat(HerbivoreResources herbivoreResources) {
        throw new UnsupportedOperationException("Predators cannot eat resources.");
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }
}
