package game;

import data.EnemyDatabase;
import game.room.RoomOutCome;
import game.room.instance.Rooms;
import model.Player;
import model.enums.GameState;
import model.enums.room.RoomType;

import java.util.Scanner;

public class GameLogic {

    public static GameState showGameMap(Scanner input, Player player) {
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