package main.java.maxkavun.simulation.entity.herbivore.resources;

public class Apple extends HerbivoreResources {

    public Apple() {
        this.icon = "\uD83C\uDF4F";
        this.health = 1 ;
    }

    @Override
    public String toString() {
        return icon;
    }

    public String getIcon() {
        return icon;
    }
}
