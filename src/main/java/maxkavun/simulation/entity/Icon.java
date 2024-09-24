package main.java.maxkavun.simulation.entity;

public enum Icon {
    PIG("🐷"),
    RABBIT("🐰"),
    WOLF("🐺"),
    EMPTY("▫"),
    GRASS("🌿"),
    TREE("🌳"),
    ROCK("🪨");

    private final String emoji;

    Icon(String emoji) {
        this.emoji = emoji;
    }

    @Override
    public String toString() {
        return emoji;
    }

}
