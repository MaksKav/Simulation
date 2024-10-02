package main.java.maxkavun.simulation.map;

import main.java.maxkavun.simulation.entity.EmptyPlace;
import main.java.maxkavun.simulation.entity.Entity;


public class Cell {

    private Coordinate coordinate;
    private Entity entity;

    public Cell(Coordinate coordinate, Entity entity) {
        this.coordinate = coordinate;
        this.entity = entity;
    }

    public Cell (Coordinate coordinate) {
        this.coordinate = coordinate;
        this.entity = new EmptyPlace();
    }

    public boolean isEmpty (){
        return entity instanceof EmptyPlace;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    @Override
    public String toString() {
        return entity.getIcon();
    }
}
