package game;

import model.Player;
import model.enums.GameState;
import model.enums.CombatTag;
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
        CombatTag combatTag = this.selectedClass(input);

        return this.createCharacter(combatTag, name);
    }


    private int showMainMenu(Scanner input) {
        System.out.println(GAME_TITLE_MENU);

        return handleDecision(0, 2);
    }

    //TODO : showInventoryMenu, handleItemDetail, dropItem, equipItem

    private Player createCharacter(CombatTag combatTag, String name) {
        return switch (combatTag) {
            case WARRIOR -> new Warrior(name);
            case MAGE -> new Mage(name);
            case ROGUE -> new Rogue(name);
            default -> null;
        };
    }

    private CombatTag selectedClass(Scanner input) {
        while (true) {
            System.out.println("\n=== CHOOSE CLASS ===");
            System.out.println("\n1. Warrior");
            System.out.println("2. Mage");
            System.out.println("3. Rogue");

            int choice = handleDecision(1, 3);

            switch (choice) {
                case 1 -> {
                    return CombatTag.WARRIOR;
                }
                case 2 -> {
                    return CombatTag.MAGE;
                }
                case 3 -> {
                    return CombatTag.ROGUE;
                }
                default -> System.out.println("Invalid selection. Try again");
            }
        }
    }


}
