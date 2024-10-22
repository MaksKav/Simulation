package main.java.maxkavun.simulation;

import main.java.maxkavun.simulation.display.SimulationDisplay;
import main.java.maxkavun.simulation.renderer.ConsoleRenderer;


public class Main {

    public static void main(String[] args) {

        ConsoleRenderer renderer = new ConsoleRenderer();
        SimulationDisplay.displayMainMenu(renderer);

    }
}

