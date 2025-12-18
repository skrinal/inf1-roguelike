package game.room.combat;

import model.interfaces.SpectralAttacker;
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

            player.beforeTurn();

            this.combatStrings.printCombatMenu(player, enemy);

            switch (Utility.handleDecision(1, 5)) {
                case 1 -> player.performeSpecialAbility(enemy);
                case 2 -> player.performeBasicAbility(enemy);
                case 3 -> player.performeUtilityAbility();
                case 4 -> player.heal(3);
                case 5 -> player.showInventory();
                default -> System.out.println("Invalid selection. Try again");
            }

            if (!enemy.isAlive()) {
                System.out.println("\n" + "Enemy has been defeated !!!");
                Utility.enterToContinue();
                break;
            }

            if (player.isUntargatable()) {
                System.out.println("Enemy attacks but misses!");

            } else if (player.canBeTargetedBy(enemy)
                        && enemy instanceof SpectralAttacker spectralAttacker) {
                spectralAttacker.performSpectralDamage(player);

            } else {

                if (this.random.nextBoolean()) {
                    enemy.performeSpecialAbility(player);
                } else {
                    enemy.performeBasicAbility(player);
                }
            }

            Utility.enterToContinue();
            player.updateStatusEffects();
        }
        player.removeAllStatusEffects();
        return player.isAlive();
    }




}
