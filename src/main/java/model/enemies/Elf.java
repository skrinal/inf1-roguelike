package model.enemies;

import model.Character;
import model.Enemy;
import model.enums.CombatTag;
import model.enums.status.StatusEffects;
import model.enums.type.EnemyType;
import model.interfaces.SpectralAttacker;

public class Elf extends Enemy implements SpectralAttacker {
    //TODO: change values
    private static final int MAX_HP = 5;
    private static final int ATTACK = 30;
    private static final int DEFENCE = 20;
    private static final int GOLD_REWARD = 100;

    private int chargeCounter = 0;

    public Elf(String name) {
        super(name, MAX_HP, ATTACK, DEFENCE, GOLD_REWARD);
    }
    public Elf(String name, int level) {
        super(name, MAX_HP, ATTACK, DEFENCE, GOLD_REWARD, level);
    }

    @Override
    public CombatTag getCombatTag() {
        return CombatTag.ELF;
    }

    @Override
    public EnemyType getEnemyType() {
        return EnemyType.ELITE;
    }

    @Override
    public void performeBasicAbility(Character target) {
        int damage = target.takeTrueDamage((int)(this.getTotalAttack() * 1.3));
        this.trueDamageAbilitySystemOut(damage, false);
    }

    @Override
    public void performeSpecialAbility(Character target) {
        if (this.isStatusEffectsEmpty()) {
            this.applyStatusEffect(StatusEffects.ELF_STRENGTH, 3);
        } else if (this.chargeCounter <= 2) {
            this.print("is charging a strong spell!!");
            this.chargeCounter++;
        } else {
            this.chargeCounter = 0;
            int damage = target.takeTrueDamage(this.getAttack() * 3);
            this.trueDamageAbilitySystemOut(damage, false);
        }
    }
}

