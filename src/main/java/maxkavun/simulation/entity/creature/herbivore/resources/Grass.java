package main.java.maxkavun.simulation.entity.creature.herbivore.resources;

public class Grass extends HerbivoreResources {

    public Grass() {
        this.icon = "🌿";
        this.health = 1 ;
    }

    @Override
    public String toString() {
        return icon;
    }

}
