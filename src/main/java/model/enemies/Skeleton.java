package model.enemies;

import model.Character;
import model.Enemy;
import model.enums.CombatTag;
import model.enums.status.StatusEffects;
import model.enums.type.EnemyType;
import utility.Utility;

public class Skeleton extends Enemy {
    // TODO: Stats
    private static final int MAX_HP = 100;
    private static final int ATTACK = 8;
    private static final int DEFENCE = 2;
    private static final int GOLD_REWARD = 10;

    public Skeleton(String name) {
        super(name, MAX_HP, ATTACK, DEFENCE, GOLD_REWARD);
    }

    public Skeleton(String name, int level) {
        super(name, MAX_HP, ATTACK, DEFENCE, GOLD_REWARD, level);
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
