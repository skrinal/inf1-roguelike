package game.room.instance;

import game.MenuLogic;
import game.room.RoomOutCome;
import game.room.combat.CombatSystem;
import model.Enemy;
import model.Player;
import model.enums.room.RoomResult;
import model.enums.room.RoomType;
import java.util.Scanner;
import static Utility.Utility.handleDecision;

public class Rooms {

    private static final String DESCRIPTION = """
            | You step into a dimly lit room.
                Two strange markings catch your eye on opposite walls
                — one shaped like an “X”, the other like a “?”.
            """;
    public static final String MAP = """
            ======== MAP ========
                     Door
                   /
                 X         ?
                   \\     /
                     You
            =====================""";
    private static final String ENEMY_ENCOUNTER = """
            ╔════════════════════════════════════════╗
            ║                                        ║
            ║    A goblin jumps from the shadows!    ║
            ║    Its rusty blade glints in the       ║
            ║    dim light.                          ║
            ║                                        ║
            ╚════════════════════════════════════════╝
            """;
    private static final String ENEMY_DEFEATED = """
            The goblin collapses with a final shriek.
            You notice it was guarding a small pouch...
            """;
    private static final String TREASURE_FOUND = """
            Behind the '?' marking, you find a small chest.
            
            Inside you discover:
            - 100 gold coins
            - 1x {Item}
            """;

    public static RoomOutCome showRoomOne(Scanner input, Player player, Enemy enemy) {
        boolean enemyCleared = false;
        boolean treasureFound = false;

        System.out.println(DESCRIPTION);
        System.out.println(MAP);

        while (true) {

            System.out.println("1. Investigate 'X' marking " + (enemyCleared ? "(cleared)" : ""));
            System.out.println("2. Investigate '?' marking " + (treasureFound ? "(found)" : ""));

            int choice;

            if (enemyCleared) {
                System.out.println("3. Proceed to next room");
                choice = handleDecision(input, 0, 3);

            } else {
                choice = handleDecision(input, 0, 2);
            }

            switch (choice) {
                case 0 -> {
                    return new RoomOutCome(RoomResult.EXIT, null); // back to menu
                }
                case 1 -> { // Enemy
                    if (!enemyCleared) {
                        enemyCleared = CombatSystem.startCombat(input, player, enemy);

                        if (!enemyCleared) {
                            return new RoomOutCome(RoomResult.DEATH, null);
                        }
                        return new RoomOutCome(RoomResult.CONTINUE, RoomType.TWO);
                    } else {
                        System.out.println("Nothing left here.");
                    }
                }
                case 2 -> { // Treasure
                    treasureFound(player);
                }
            }


//            switch (choice) {
//                case 0 -> {
//                    System.out.println("You leave the dungeon");
//                    return 0;
//                }
//                case 1 -> {
//                    if (!enemyCleared) {
//                        System.out.println("You encounter an enemy!");
//                        // Combat logic here
//                        // if (player dies) return -1;
//                        enemyCleared = true;
//                        System.out.println("Enemy defeated!");
//                    } else {
//                        System.out.println("Nothing left here.");
//                    }
//                }
//                case 2 -> {
//                    if (!treasureFound) {
//                        System.out.println("You found treasure!");
//                        // Add treasure to player
//                        treasureFound = true;
//                    } else {
//                        System.out.println("Nothing left here.");
//                    }
//                }
//                case 3 -> {
//                    if (enemyCleared && treasureFound) {
//                        System.out.println("Moving to next room...");
//                        return 2; // Go to room 2
//                    } else {
//                        System.out.println("You should explore both markings first!");
//                    }
//                }
//            }
        }
    }

    private static void treasureFound(Player player) {
        System.out.println(TREASURE_FOUND);

    }


//    @Override
//    protected Item getClassItem(PlayerClass playerClass) {
//        switch (playerClass) {
//            case MAGE -> {
//                return ItemDatabase.WOODEN_STAFF;
//            }
//            case ROGUE -> {
//                return ItemDatabase.IRON_DAGGER;
//            }
//            case WARRIOR -> {
//                return ItemDatabase.IRON_SWORD;
//            }
//            default -> throw new IllegalStateException("Unexpected value: " + playerClass);
//        }
//    }
}
