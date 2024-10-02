package main.java.maxkavun.simulation.entity;

public class Rock extends Barrier{

    public Rock() {
        this.icon = "\uD83E\uDEA8";
    }

    @Override
    public String toString() {
        return icon;
    }

    public String getIcon() {
        return icon;
    }
}
