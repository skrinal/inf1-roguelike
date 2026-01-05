package game.room.combat;

import model.Item;
import model.interfaces.Boss;
import model.interfaces.SpectralAttacker;
import model.strings.CombatStrings;
import model.Enemy;
import model.Player;
import utility.Utility;

import java.util.Random;

/**
 * The CombatSystem class handles the turn-based combat logic between a player and an enemy,
 * managing their actions, abilities, and statuses.
 */
public class CombatSystem {
    private final CombatStrings combatStrings = new CombatStrings();
    private final Random random = new Random();

    /**
     * Initiates and handles combat between the player and an enemy.
     * The combat continues in a turn-based until either the player or the enemy is defeated.
     * Handles various combat scenarios such as player turns, enemy actions, and status effect updates.
     * In the end rewards the player with experience points and drops a consumable item.
     */
    public boolean startCombat(Player player, Enemy enemy) {
        this.combatStrings.printCombatStart();

        while (player.isAlive() && enemy.isAlive()) {

            player.updateStatusEffects();
            if (!player.isAlive()) {
                break;
            }
            this.handlePlayerTurn(player, enemy);

            enemy.updateStatusEffects();
            if (this.checkIfEnemyDead(enemy)) {
                break;
            }
            this.handleEnemyTurn(player, enemy);
        }

        if (player.isAlive()) {
            this.handleVictory(player, enemy);
        }

        return player.isAlive();
    }

    private boolean handlePlayerAction(Player player, Enemy enemy) {
        player.beforeTurn();

        this.combatStrings.printCombatMenu(player, enemy);

        switch (Utility.handleDecision(1, 5)) {
            case 1 -> player.performeBasicAbility(enemy);
            case 2 -> player.performeSpecialAbility(enemy);
            case 3 -> player.performeUtilityAbility();
            case 4 -> player.resting();
            case 5 -> {
                return player.showInventory();
            }
            default -> System.out.println("Invalid selection. Try again");
        }
        return false;
    }

    private void handlePlayerTurn(Player player, Enemy enemy) {
        boolean playerTurn = true;
        while (playerTurn) {
            playerTurn = this.handlePlayerAction(player, enemy);
        }
    }

    private void handleEnemyTurn(Player player, Enemy enemy) {
        if (enemy instanceof Boss boss) {
            boss.onBossTurn(player);
            return;
        }

        if (player.isUntargetable()) {
            enemy.missHit();
            return;
        }

        if (enemy instanceof SpectralAttacker spectralAttacker && player.canBeTargetedBy(enemy)) {
            spectralAttacker.performSpectralDamage(player);
            return;
        }

        if (this.random.nextBoolean()) {
            enemy.performeSpecialAbility(player);
        } else {
            enemy.performeBasicAbility(player);
        }
    }

    private boolean checkIfEnemyDead(Enemy enemy) {
        if (!enemy.isAlive()) {
            this.combatStrings.printEnemyDefeated();
            Utility.enterToContinue();
            return true;
        }
        return false;
    }

    private void handleVictory(Player player, Enemy enemy) {
        player.removeAllStatusEffects();
        player.restoreMaxPower();

        player.gainExperience(enemy.getXpReward());

        Item item = enemy.getConsumableDrop();
        if (item != null) {
            player.addItem(item);
        }
    }

}
