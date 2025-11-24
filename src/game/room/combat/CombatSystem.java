package game.room.combat;

import model.strings.CombatStrings;
import model.Enemy;
import model.Player;
import utility.Utility;

import java.util.Random;

public class CombatSystem {
    private final CombatStrings combatStrings = new CombatStrings();
    private final Random random = new Random();

    public boolean startCombat(Player player, Enemy enemy) {
        System.out.println("\n=== COMBAT START ===");

        while (player.isAlive() && enemy.isAlive()) {
            this.combatStrings.printCombatMenu(player, enemy);

            switch (Utility.handleDecision(1, 5)) {
                case 1 -> player.performeSpecialAbility(enemy);
                case 2 -> player.performeUtilityAbitlity();
                case 3 -> player.performeBasicAbility(enemy);
                case 4 -> player.restorePower(10);
                case 5 -> {
                    // TODO: Item
                }
            }

            if (!enemy.isAlive()) {
                System.out.println("\n" + "Enemy has been defeated !!!");
                Utility.enterToContinue();
                break;
            }

            switch (this.random.nextInt(2) + 1 ) {
                case 1 -> enemy.performeSpecialAbility(player);
                case 2 -> enemy.performeAttack(player);
            }
        }

        return player.isAlive();
    }




}
