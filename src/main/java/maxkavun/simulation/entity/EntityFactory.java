package main.java.maxkavun.simulation.entity;

import main.java.maxkavun.simulation.entity.herbivore.Pig;
import main.java.maxkavun.simulation.entity.herbivore.Rabbit;
import main.java.maxkavun.simulation.entity.herbivore.resources.Apple;
import main.java.maxkavun.simulation.entity.herbivore.resources.Grass;
import main.java.maxkavun.simulation.entity.predator.Wolf;
import main.java.maxkavun.simulation.map.Coordinate;

public class EntityFactory {
    private static final int MAX_RANDOM_VALUE = 201;

    public Entity createEntity(Coordinate coordinate) {
        int randomInt = (int) (Math.random() * MAX_RANDOM_VALUE);
        return createEntityBasedOnRandomValue(randomInt);
    }


    private Entity createEntityBasedOnRandomValue(int randomInt) {

        if (randomInt <= 3) {
            return new Rock();
        } else if (randomInt <= 8) {
            return new Tree();
        } else if (randomInt == 9) {
            return new Pig();
        } else if (randomInt == 10) {
            return new Rabbit();
        } else if (randomInt == 11) {
            return new Wolf();
        } else if (randomInt <= 14) {
            return new Grass();
        } else if (randomInt <= 17) {
            return new Apple();
        } else {
            return new EmptyPlace();
        }
    }
}
