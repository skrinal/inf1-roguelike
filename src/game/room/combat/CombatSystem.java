package game.room.combat;

import game.MenuLogic;
import model.Enemy;
import model.Player;
import java.util.Scanner;

import static game.strings.CombatStrings.printCombatMenu;

public class CombatSystem {
    public static boolean startCombat(Scanner input, Player player, Enemy enemy) {
        System.out.println("\n=== COMBAT START===");

        while (player.isAlive() && enemy.isAlive()) {
            printCombatMenu(player, enemy);
            MenuLogic.handleDecision(input, 0, 2);
        }

        return false; // Dead
    }
//    private String builderPlayerStats(Player player) {
//        StringBuilder sb = new StringBuilder();
//        sb.append(ClassPower.MANA);
//    }



}
