package main.java.maxkavun.simulation.entity;

public class EmptyPlace extends Entity {


    public EmptyPlace() {
        this.icon = "⬛";
    }

    @Override
    public String toString() {
        return icon;
    }

}
