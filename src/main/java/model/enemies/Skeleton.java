package model.enemies;

import data.Items;
import model.Character;
import model.Enemy;
import model.enums.CombatTag;
import model.enums.enemy.EnemyStats;
import model.enums.status.StatusEffects;
import model.enums.type.EnemyType;

public class Skeleton extends Enemy {

    public Skeleton(String name) {
        super(name,
                EnemyStats.SKELETON.getBaseMaxHp(),
                EnemyStats.SKELETON.getBaseAttack(),
                EnemyStats.SKELETON.getBaseDefence(),
                EnemyStats.SKELETON.getGoldReward(),
                EnemyStats.SKELETON.getXpReward(),
                Items.HEALTH_POTION.getItem()

        );
    }

    public Skeleton(String name, int level) {
        super(name,
                EnemyStats.SKELETON.getBaseMaxHp(),
                EnemyStats.SKELETON.getBaseAttack(),
                EnemyStats.SKELETON.getBaseDefence(),
                EnemyStats.SKELETON.getGoldReward(),
                EnemyStats.SKELETON.getXpReward(),
                level,
                Items.HEALTH_POTION.getItem()
        );
    }

    @Override
    public CombatTag getCombatTag() {
        return CombatTag.SKELETON;
    }

    @Override
    public EnemyType getEnemyType() {
        return EnemyType.TRASH;
    }

    @Override
    public void performBasicAbility(Character target) {
        int damage = target.takeDamage(this.getTotalAttack(), this);
        this.damageAbilitySystemOut(damage, false);
    }

    @Override
    public void performSpecialAbility(Character target) {
        int damage = target.takeDamage(this.getTotalAttack(), this);
        this.damageAbilitySystemOut(damage, false);

        if (Math.random() <= 0.3) {
            target.applyStatusEffect(StatusEffects.SKELETON_CURSE, 2);

        }
    }
}
