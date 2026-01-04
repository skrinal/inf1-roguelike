package game.room.combat;

import model.interfaces.Boss;
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
        this.combatStrings.printCombatStart();

        while (player.isAlive() && enemy.isAlive()) {

            if (this.checkIfEnemyDead(enemy)) {
                this.getXp(player, enemy);
                break;
            }

            player.beforeTurn();

            boolean playerTurn = true;
            while (playerTurn) {
                playerTurn = this.handlePlayerTurn(player, enemy);
            }


            if (this.checkIfEnemyDead(enemy)) {
                this.getXp(player, enemy);
                break;
            }

            if (enemy instanceof Boss boss) {
                boss.onBossTurn(player);
            } else {
                if (player.isUntargatable()) {
                    System.out.println("Enemy lost vision of you!");

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
            }

            player.updateStatusEffects();
            enemy.updateStatusEffects();
        }

        player.removeAllStatusEffects();
        player.restoreMaxPower();
        return player.isAlive();
    }

    private boolean handlePlayerTurn(Player player, Enemy enemy) {
        this.combatStrings.printCombatMenu(player, enemy);

        switch (Utility.handleDecision(1, 5)) {
            case 1 -> player.performeBasicAbility(enemy);
            case 2 -> player.performeSpecialAbility(enemy);
            case 3 -> player.performeUtilityAbility();
            case 4 -> player.heal(5 + player.getLevel());
            case 5 -> {
                return player.showInventory();
            }
        }
        return false;
    }

    private boolean checkIfEnemyDead(Enemy enemy) {
        if (!enemy.isAlive()) {
            System.out.println("\n" + "Enemy has been defeated !!!");
            Utility.enterToContinue();
            return true;
        }
        return false;
    }

    private void getXp(Player player, Enemy enemy) {
        player.gainExperience(enemy.getXpReward());
    }

}
