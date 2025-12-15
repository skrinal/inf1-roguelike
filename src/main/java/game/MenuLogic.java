package game;

import model.Player;
import model.enums.GameState;
import model.enums.PlayerClass;
import model.players.Mage;
import model.players.Rogue;
import model.players.Warrior;

import java.util.Scanner;

import static utility.Utility.handleDecision;
import static model.strings.MenuStrings.GAME_TITLE_MENU;

public class MenuLogic {

    public GameState handleMenu(Scanner input, Player player) {
        int choice = this.showMainMenu(input);
        return switch (choice) {
            case 1 -> player == null ? GameState.CHARACTER_CREATION : GameState.GAME;
            case 0 -> GameState.EXIT;
            default -> GameState.MAIN_MENU;
        };
    }

    public Player handleCharacterCreation(Scanner input) {
        System.out.println("\n=== CHARACTER CREATION ===\n");
        System.out.print("Name your character: ");
        String name = input.nextLine();
        PlayerClass playerClass = this.selectedClass(input);

        return this.createCharacter(playerClass, name);
    }


    private int showMainMenu(Scanner input) {
        System.out.println(GAME_TITLE_MENU);

        return handleDecision(0, 2);
    }

    //TODO : showInventoryMenu, handleItemDetail, dropItem, equipItem

    private Player createCharacter(PlayerClass playerClass, String name) {
        return switch (playerClass) {
            case WARRIOR -> new Warrior(name);
            case MAGE -> new Mage(name);
            case ROGUE -> new Rogue(name);
        };
    }

    private PlayerClass selectedClass(Scanner input) {
        while (true) {
            System.out.println("\n=== CHOOSE CLASS ===");
            System.out.println("\n1. Warrior");
            System.out.println("2. Mage");
            System.out.println("3. Rogue");

            int choice = handleDecision(1, 3);

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
                default -> System.out.println("Invalid selection. Try again");

            }
        }
    }


}
