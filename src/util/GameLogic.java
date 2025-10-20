package util;

import model.Player;

import java.util.Scanner;

public class GameLogic {
    public static void showGameMap(Scanner input, Player player){
        System.out.println("----------------------------");
        System.out.println(GameStrings.DUNGEON_ROOM_ONE);

        System.out.println("====== MAP ======\n");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("        X              X");
        System.out.println();
        System.out.println("            You");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("==================\n");

        int choice = MenuLogic.handleDecision(input, 1, 2);

    }
}
