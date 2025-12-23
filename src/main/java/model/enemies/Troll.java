package model.enemies;

import model.Character;
import model.Enemy;
import model.enums.CombatTag;
import model.enums.type.EnemyType;
import utility.Utility;

public class Troll extends Enemy {
    //TODO Change values
    private static final int MAX_HP = 5;
    private static final int ATTACK = 30;
    private static final int DEFENCE = 20;
    private static final int GOLD_REWARD = 100;

    public Troll(String name) {
        super(name, MAX_HP, ATTACK, DEFENCE, GOLD_REWARD);
    }
    public Troll(String name, int level) {
        super(name, MAX_HP, ATTACK, DEFENCE, GOLD_REWARD, level);
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
    public void performeBasicAbility(Character target) {
        int damage = target.takeDamage((int)(this.getTotalAttack() * 1.3), this);
        this.damageAbilitySystemOut(damage, false);
    }

    @Override
    public void performeSpecialAbility(Character target) {
        if (Utility.getRandomDouble() <= 0.4) {
            int damage = target.takeTrueDamage((int)(this.getTotalAttack() * 1.2));
            this.damageAbilitySystemOut(damage, false);
        } else {
            int damage = target.takeTrueDamage((int)(this.getTotalAttack() * 1.6));
            this.trueDamageAbilitySystemOut(damage, false);
        }
    }
}
