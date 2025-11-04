package game;

import data.EnemyDatabase;
import game.room.RoomOutCome;
import game.room.combat.CombatSystem;
import model.Enemy;
import model.Player;
import model.enums.GameState;
import model.enums.room.RoomResult;
import model.enums.room.RoomType;

import java.util.Scanner;

public class GameLogic {

    public static GameState showGameMap(Scanner input, Player player) {
        RoomType currentRoom = RoomType.ONE;
        boolean inDungeon = true;

        while (inDungeon) {
            RoomOutCome outCome = switch (currentRoom) {
                case ONE -> showRoomOne(input, player, EnemyDatabase.ROOM_ONE);
                case TWO -> null;
                case THREE -> null;
                case FOUR -> null;
                case FIVE -> null;

            };

            switch (outCome.result) {
                case EXIT -> {
                    inDungeon = false;
                    return GameState.MAIN_MENU;
                }
                case DEATH -> {
                    inDungeon = false;
                    return GameState.DEATH;
                }
                case COMPLETED -> {
                    inDungeon = false;
                    return GameState.COMPLETE;
                }
            }
        }
        return GameState.COMPLETE; // Completed dungeon
    }

    private static RoomOutCome showRoomOne(Scanner input, Player player, Enemy enemy) {
        boolean enemyCleared = false;
        boolean treasureFound = false;

        while (true) {

            System.out.println("1. Investigate 'X' marking " + (enemyCleared ? "(cleared)" : ""));
            System.out.println("2. Investigate '?' marking " + (treasureFound ? "(found)" : ""));

            int choice;

            if (enemyCleared) {
                System.out.println("3. Proceed to next room");
                choice = MenuLogic.handleDecision(input, 0, 3);

            } else {
                choice = MenuLogic.handleDecision(input, 0, 2);
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

    private static int showRoomTwo(Scanner input, Player player) {
        System.out.println("Room 2");
        return 3;
    }

    private static int showRoomThree(Scanner input, Player player) {
        System.out.println("Room 3 - Final room");
        return 0; // Exit dungeon after completion
    }

}