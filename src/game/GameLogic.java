package game;

import model.Player;

import java.util.Scanner;

public class GameLogic {
    public static int showGameMap(Scanner input, Player player){
        int currentRoom = 1;
        boolean inDungeon = true;

        while (inDungeon){
            int nextRoom = switch (currentRoom){
                case 1 -> showRoomOne(input, player);
                case 2 -> showRoomTwo(input, player);
                case 3 -> showRoomThree(input, player);
                default -> 0;
            };

            if (nextRoom == -1) {
                return -1; // Player died or quit
            } else if (nextRoom == 0) {
                inDungeon = false;
            } else if (nextRoom == -2) {
                return -2;
            } else {
                currentRoom = nextRoom;
            }
        }
        return 0; // Completed dungeon
    }

    private static int showRoomOne(Scanner input, Player player){
        boolean enemyCleared = false;
        boolean treasureFound = false;

        while (true) {
            System.out.println(GameStrings.DUNGEON_ROOM_ONE_TEXT);
            System.out.println(GameStrings.DUNGEON_ROOM_ONE_MAP);
            System.out.println("1. Investigate 'X' marking " + (enemyCleared ? "(cleared)" : ""));
            System.out.println("2. Investigate '?' marking " + (treasureFound ? "(found)" : ""));

            int choice;

            if (enemyCleared){
                System.out.println("3. Proceed to next room");
                choice = MenuLogic.handleDecision(input, 0, 3);

            } else {
                choice = MenuLogic.handleDecision(input, 0, 2);
            }

            switch (choice){
                case 0 -> {
                    return -1; // back to menu
                }
                case 1 -> { // Enemy

                    return 1;
                }
                case 2 ->{ // Treasure
                    return 2;

                }
            }



            switch (choice) {
                case 0 -> {
                    System.out.println("You leave the dungeon");
                    return 0;
                }
                case 1 -> {
                    if (!enemyCleared) {
                        System.out.println("You encounter an enemy!");
                        // Combat logic here
                        // if (player dies) return -1;
                        enemyCleared = true;
                        System.out.println("Enemy defeated!");
                    } else {
                        System.out.println("Nothing left here.");
                    }
                }
                case 2 -> {
                    if (!treasureFound) {
                        System.out.println("You found treasure!");
                        // Add treasure to player
                        treasureFound = true;
                    } else {
                        System.out.println("Nothing left here.");
                    }
                }
                case 3 -> {
                    if (enemyCleared && treasureFound) {
                        System.out.println("Moving to next room...");
                        return 2; // Go to room 2
                    } else {
                        System.out.println("You should explore both markings first!");
                    }
                }
            }
        }
    }

    private static int showRoomTwo(Scanner input, Player player){
        System.out.println("Room 2");
        return 3;
    }

    private static int showRoomThree(Scanner input, Player player){
        System.out.println("Room 3 - Final room");
        return 0; // Exit dungeon after completion
    }

}