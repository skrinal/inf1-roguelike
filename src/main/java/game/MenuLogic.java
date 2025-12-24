package game;

import model.Player;
import model.enums.GameState;
import model.enums.CombatTag;
import model.players.Mage;
import model.players.Rogue;
import model.players.Warrior;
import model.strings.MenuStrings;

import java.util.Scanner;

import static utility.Utility.handleDecision;

public class MenuLogic {

    public GameState handleMenu(Player player) {
        int choice = this.showMainMenu();
        return switch (choice) {
            case 1 -> player == null ? GameState.CHARACTER_CREATION : GameState.GAME;
            case 2 -> GameState.LOAD_GAME;
            case 0 -> GameState.SAVE_GAME;
            default -> GameState.MAIN_MENU;
        };
    }

    public Player handleCharacterCreation(Scanner input) {
        System.out.println(MenuStrings.CHARACTER_CREATION);
        System.out.print("Name your character: ");
        String name = input.nextLine();
        CombatTag combatTag = this.selectedClass();

        return this.createCharacter(combatTag, name);
    }


    private int showMainMenu() {
        System.out.println(MenuStrings.GAME_TITLE_MENU);

        return handleDecision(0, 3);
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

    private CombatTag selectedClass() {
        while (true) {
            System.out.println(MenuStrings.CHOOSE_CLASS);
            System.out.println("1) Warrior");
            System.out.println("2) Mage");
            System.out.println("3) Rogue");

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
