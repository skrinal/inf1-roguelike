package util;

import model.enums.PlayerClass;
import model.players.Mage;
import model.players.Rogue;
import model.players.Warrior;

import java.util.Scanner;

import static util.GameStrings.GAME_TITLE;

public class MenuLogic {

    public static void printGameMenu() {
        System.out.println(GAME_TITLE);
        System.out.println("==========================");
        System.out.println("1. Start Game");
        System.out.println("2. Settings - Not implemented yet");
        System.out.println("3. Exit");
        System.out.print("Select: ");
    }


    public static void clearConsole() {
        for (int i = 0; i < 15; i++) {
            System.out.println();
        }
    }

    public static model.Character startGame(Scanner input) throws InterruptedException {
        System.out.println("\n=== CHARACTER CREATION ===\n");
        System.out.print("Name your character: ");
        String name = input.nextLine();
        PlayerClass playerClass = printClassOptions(input);

        return createCharacter(playerClass, name);
    }

    private static PlayerClass printClassOptions(Scanner input) {
        while (true){
            System.out.println("\n1. Warrior");
            System.out.println("2. Mage");
            System.out.println("3. Rogue");
            System.out.print("Choose a class: ");

            if (!input.hasNextInt()) {
                System.out.println("Invalid selection. Try again");
                input.next();
                continue;
            }
            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1 -> {
                    return PlayerClass.WARRIOR;
                }
                case 2 -> {
                    return PlayerClass.MAGE;
                }
                case 3 -> {
                    return PlayerClass.ROGUE;
                }
                default -> {
                    System.out.println("Invalid selection. Try again");
                }
            }
        }
    }

    private static model.Character createCharacter(PlayerClass playerClass, String name) {
        return switch (playerClass) {
            case WARRIOR -> new Warrior(name);
            case MAGE -> new Mage(name);
            case ROGUE -> new Rogue(name);
        };
    }



}
