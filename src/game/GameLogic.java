package game;

import data.EnemyDatabase;
import game.room.RoomOutCome;
import game.room.instance.Rooms;
import model.Player;
import model.enums.GameState;
import model.enums.room.RoomType;
import java.util.Scanner;
import static Utility.Utility.handleDecision;
import static game.strings.MenuStrings.GAME_MENU_OPTIONS;

public class GameLogic {

    public GameState handleGame(Scanner input) {
        int choice = showGameMenu(input);
        return switch (choice) {
            case 1 -> GameState.DUNGEON;
            case 2 -> GameState.STATS;
            case 0 -> GameState.MAIN_MENU;
            default -> GameState.GAME;
        };
    }

    public GameState handleDungeon(Scanner input, Player player) {
        System.out.println("\n=== DUNGEON ===");
        GameState result = showGameMap(input, player);

        switch (result) {
            case DEATH -> {
                return GameState.DEATH;
            }
            case COMPLETE -> {
                return GameState.COMPLETE;
            }
            default -> {
                return GameState.MAIN_MENU;
            }
        }
    }

    private int showGameMenu(Scanner input) {
        System.out.println("\n" + GAME_MENU_OPTIONS);

        return handleDecision(input, 0, 3);
    }

    private GameState showGameMap(Scanner input, Player player) {
        RoomType currentRoom = RoomType.ONE;
        boolean inDungeon = true;
        Object dada;
        while (inDungeon) {
            RoomOutCome outCome = switch (currentRoom) {
                case ONE -> Rooms.showRoomOne(input, player, EnemyDatabase.ROOM_ONE);
                case TWO -> null;
                case THREE -> null;
                case FOUR -> null;
                case FIVE -> null;

            };

            switch (outCome.result()) {
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



    private static int showRoomTwo(Scanner input, Player player) {
        System.out.println("Room 2");
        return 3;
    }

    private static int showRoomThree(Scanner input, Player player) {
        System.out.println("Room 3 - Final room");
        return 0; // Exit dungeon after completion
    }

}