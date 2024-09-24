package main.java.maxkavun.simulation.entity;

public class Wolf extends Predator{

    private final String icon = "\uD83D\uDC3A" ; // 🐺

    public Wolf() {
        this.damage = 100;
        this.speed = 2;
    }
}
