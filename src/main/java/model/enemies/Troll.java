package model.enemies;

import data.Items;
import model.Character;
import model.Enemy;
import model.enums.CombatTag;
import model.enums.enemy.EnemyStats;
import model.enums.type.EnemyType;
import utility.Utility;

/**
 * The Troll class represents a specific type of enemy in the game derived from the Enemy class.
 * Trolls are hostile creatures with distinct attributes, behaviors, and abilities.
 * This class provides the implementation of the Troll's specific stats, abilities, and type information.
 */
public class Troll extends Enemy {

    public Troll(String name) {
        super(name,
                EnemyStats.TROLL.getBaseMaxHp(),
                EnemyStats.TROLL.getBaseAttack(),
                EnemyStats.TROLL.getBaseDefence(),
                EnemyStats.TROLL.getGoldReward(),
                EnemyStats.TROLL.getXpReward(),
                Items.HEALTH_POTION.getItem()

        );
    }
    public Troll(String name, int level) {
        super(name,
                EnemyStats.TROLL.getBaseMaxHp(),
                EnemyStats.TROLL.getBaseAttack(),
                EnemyStats.TROLL.getBaseDefence(),
                EnemyStats.TROLL.getGoldReward(),
                EnemyStats.TROLL.getXpReward(),
                level,
                Items.HEALTH_POTION.getItem()
        );
    }

    @Override
    public EnemyType getEnemyType() {
        return EnemyType.TRASH;
    }

    @Override
    public CombatTag getCombatTag() {
        return CombatTag.TROLL;
    }

    @Override
    public void performBasicAbility(Character target) {
        int damage = target.takeDamage((int)(this.getTotalAttack() * 1.3), this);
        this.damageAbilitySystemOut(damage, false);
    }

    @Override
    public void performSpecialAbility(Character target) {
        if (Math.random() <= 0.4) {
            int damage = target.takeTrueDamage((int)(this.getTotalAttack() * 1.2));
            this.damageAbilitySystemOut(damage, false);
        } else {
            int damage = target.takeTrueDamage((int)(this.getTotalAttack() * 1.6));
            this.trueDamageAbilitySystemOut(damage, false);
        }
    }
}
