package model.enemies;

import data.Items;
import model.Character;
import model.Enemy;
import model.Player;
import model.enums.CombatTag;
import model.enums.enemy.EnemyStats;
import model.enums.status.StatusEffects;
import model.enums.type.EnemyType;
import model.interfaces.Boss;

/**
 * Represents a powerful Dragon enemy in the game, categorized as an elite enemy and implementing
 * the Boss interface.
 * Dragons are challenging opponents with unique abilities and a specialization in delivering big
 * attacks to players.
 */
public class Dragon extends Enemy implements Boss {

    private int chargeCounter = 0;

    /**
     * Constructs a Dragon enemy with the specified name.
     * The Dragon is initialized with predefined stats retrieved from the EnemyStats enum.
     * Each Dragon is assigned a maximum health, attack, defense, gold reward,
     * experience reward, and a consumable item drop.
     *
     * @param name the name of the Dragon enemy
     */
    public Dragon(String name) {
        super(name,
                EnemyStats.DRAGON.getBaseMaxHp(),
                EnemyStats.DRAGON.getBaseAttack(),
                EnemyStats.DRAGON.getBaseDefence(),
                EnemyStats.DRAGON.getGoldReward(),
                EnemyStats.DRAGON.getXpReward(),
                Items.HEALTH_CHALICE.getItem()
        );
    }

    /**
     * Constructs a Dragon enemy with the specified name and level.
     * The Dragon is initialized with predefined stats retrieved from the EnemyStats enum.
     * The properties include maximum health, attack, defense, gold reward,
     * experience reward, and a consumable item drop.
     */
    public Dragon(String name, int level) {
        super(name,
                EnemyStats.DRAGON.getBaseMaxHp(),
                EnemyStats.DRAGON.getBaseAttack(),
                EnemyStats.DRAGON.getBaseDefence(),
                EnemyStats.DRAGON.getGoldReward(),
                EnemyStats.DRAGON.getXpReward(),
                level,
                Items.HEALTH_CHALICE.getItem()
        );
    }

    /**
     * Return type of the enemy.
     */
    @Override
    public EnemyType getEnemyType() {
        return EnemyType.ELITE;
    }

    /**
     * Returns the combat tag associated with the Dragon class.
     */
    @Override
    public CombatTag getCombatTag() {
        return CombatTag.DRAGON;
    }

    /**
     * Executes the basic ability of the Dragon, dealing damage to a target character.
     * This method calculates the damage based on the Dragon's total attack stat
     * scaled by a predefined multiplier and applies the damage to the target.
     */
    @Override
    public void performBasicAbility(Character target) {
        int damage = target.takeDamage((int)(this.getTotalAttack() * 1.4), this);
        this.damageAbilitySystemOut(damage, false);
    }

    /**
     * Executes the special ability of the Dragon. Depending on the Dragon's
     * current status and charge counter, it may roar to increase its power,
     * gather fire for a fire breath attack, or unleash a devastating fire
     * breath attack that deals high damage and applies a burn status effect
     * to the target.
     *
     */
    @Override
    public void performSpecialAbility(Character target) {
        if (!this.hasStatusEffect(StatusEffects.DRAGON_MIGHT)) {
            this.print(this.getName() + " lets out a terrifying roar! Its power increases!");
            this.applyStatusEffect(StatusEffects.DRAGON_MIGHT, 4);
            
        } else if (this.chargeCounter < 2) {
            this.print(this.getName() + " is gathering fire in its throat... " + (2 - this.chargeCounter) + " turns left!");
            this.chargeCounter++;
            
        } else {
            this.chargeCounter = 0;
            this.print(this.getName() + " unleashes a devastating FIRE BREATH!");
            int damage = target.takeTrueDamage((int)(this.getTotalAttack() * 4.0));
            this.trueDamageAbilitySystemOut(damage, false);
            target.applyStatusEffect(StatusEffects.BURN, 3);
        }
    }

    /**
     * Executes the logic defining the Dragon's actions on its turn during a combat encounter.
     * This method determines the type of attack or action the Dragon will perform based on
     * its current state, specific conditions, and the target player's status effects.
     */
    @Override
    public void onBossTurn(Player player) {
        if (player.isUntargetable() || player.hasStatusEffect(StatusEffects.INVISIBILITY)) {
            this.performSpectralDamage(player);
            return;
        }

        if (this.chargeCounter > 0) {
            this.performSpecialAbility(player);
            return;
        }

        if (!this.hasStatusEffect(StatusEffects.DRAGON_MIGHT)) {
            this.performSpecialAbility(player);
            this.performBasicAbility(player);
            return;
        }

        if (Math.random() < 0.6) {
            this.performSpecialAbility(player);
        } else {
            this.performBasicAbility(player);
        }
    }
}
