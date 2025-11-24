package game.room.combat;

import model.strings.CombatStrings;
import model.Enemy;
import model.Player;

import java.util.Random;
import java.util.Scanner;
import static utility.Utility.handleDecision;

public class CombatSystem {
    private final CombatStrings combatStrings = new CombatStrings();
    private final Random random = new Random();

    public boolean startCombat(Scanner input, Player player, Enemy enemy) {
        System.out.println("\n=== COMBAT START ===");

        while (player.isAlive() && enemy.isAlive()) {
            this.combatStrings.printCombatMenu(player, enemy);

            switch (handleDecision(input, 1, 5)) {
                case 1 -> player.performeSpecialAbility(enemy);
                case 2 -> player.performeUtilityAbitlity();
                case 3 -> player.performeBasicAbility(enemy);
                case 4 -> player.restorePower(10);
                case 5 -> {
                    // TODO: Item
                }
            }

            if (!enemy.isAlive()) {
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
