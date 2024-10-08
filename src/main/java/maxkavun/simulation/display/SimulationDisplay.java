package main.java.maxkavun.simulation.display;
import main.java.maxkavun.simulation.actions.InitActions;
import main.java.maxkavun.simulation.actions.TurnActions;
import main.java.maxkavun.simulation.map.SimulationMap;
import main.java.maxkavun.simulation.renderer.Renderer;

import java.util.InputMismatchException;
import java.util.Scanner;

public class SimulationDisplay {


    public static void showInitialDisplay() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the 2D World Simulation");
        System.out.println("1. Start Simulation");
        System.out.println("2. Generate Map");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                int moves = getNumberOfMoves(scanner);
                InitActions.startSimulation(moves);
                break;
            case 2:
                System.out.println("Generating map...\n");
                Renderer.drawMap(SimulationMap.getInstance());
                showInitialDisplay();
                break;
            case 3:
                System.out.println("Exiting... \n" );
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice, please try again.");
                showInitialDisplay();
                break;
        }
    }


    public static void showMiddleDisplay() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Continue simulation");
        System.out.println("2. Add random animal to the map");
        System.out.println("3. Add herbivore to the map");
        System.out.println("4. Add predator to the map");
        System.out.println("5. End simulation");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                System.out.print("How many moves would you like to make? ");
                int moves = getNumberOfMoves(scanner);
                InitActions.startSimulation(moves);
                break;
            case 2:
                System.out.print("How many animals would you like to add to the map? (No more than 3)");
                TurnActions.addAnimals(scanner, "all types");
                System.out.println("Random animals were successfully added on the card \n");
                showMiddleDisplay();
                break;
            case 3:
                System.out.print("How many herbivores would you like to add to the map? (No more than 3)");
                TurnActions.addAnimals(scanner, "herbivores");
                System.out.println("Herbivores were successfully added on the card \n");
                showMiddleDisplay();
                break;
            case 4:
                System.out.print("How many predators would you like to add to the map? (No more than 3)");
                TurnActions.addAnimals(scanner, "predators");
                System.out.println("Predators were successfully added on the card \n");
                showMiddleDisplay();
                break;
            case 5:
                System.out.println("Ending simulation...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice, please try again.");
                showMiddleDisplay();
                break;
        }
    }


    public static void showFinalDisplay() {
        System.out.println("1. Start a new simulation");
        System.out.println("2. Exit");
        System.out.print("Choose an option: ");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                SimulationMap.resetInstance();
                InitActions.creatures.clear();
                showInitialDisplay();
                break;
            case 2:
                System.out.println("Exiting...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice, please try again.");
                showFinalDisplay();
                break;
        }
    }


    private static int getNumberOfMoves(Scanner scanner) {
        while (true) {
            try {
                System.out.print("How many moves would you like to generate for the first cycle? ");
                int moves = scanner.nextInt();
                if (moves <= 0) {
                    System.out.println("Please enter a positive number.");
                    continue;
                }
                return moves;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please enter a number.");
                scanner.next();
            }
        }
    }
}
