package model.enemies;

import model.Character;
import model.Enemy;
import model.enums.CombatTag;
import model.enums.enemy.EnemyStats;
import model.enums.status.StatusEffects;
import model.enums.type.EnemyType;
import utility.Utility;

public class Skeleton extends Enemy {

    public Skeleton(String name) {
        super(name,
                EnemyStats.SKELETON.getBaseMaxHp(),
                EnemyStats.SKELETON.getBaseAttack(),
                EnemyStats.SKELETON.getBaseDefence(),
                EnemyStats.SKELETON.getGoldReward(),
                EnemyStats.SKELETON.getXpReward()
        );
    }

    public Skeleton(String name, int level) {
        super(name,
                EnemyStats.SKELETON.getBaseMaxHp(),
                EnemyStats.SKELETON.getBaseAttack(),
                EnemyStats.SKELETON.getBaseDefence(),
                EnemyStats.SKELETON.getGoldReward(),
                EnemyStats.SKELETON.getXpReward(),
                level
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
    public void performeBasicAbility(Character target) {
        int damage = target.takeDamage(this.getTotalAttack(), this);
        this.damageAbilitySystemOut(damage, false);
    }

    @Override
    public void performeSpecialAbility(Character target) {
        int damage = target.takeDamage(this.getTotalAttack(), this);
        this.damageAbilitySystemOut(damage, false);

        if (Utility.getRandomDouble() <= 0.3) {
            target.applyStatusEffect(StatusEffects.SKELETON_CURSE, 2);

        }
    }
}
