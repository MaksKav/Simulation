package main.java.maxkavun.simulation.entity.herbivore.resources;

public class Grass extends HerbivoreResources {

    public Grass() {
        this.icon = "🌿";
        this.health = 2 ;
    }

    @Override
    public String toString() {
        return icon;
    }

    public String getIcon() {
        return icon;
    }
}
