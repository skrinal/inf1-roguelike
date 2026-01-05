package model.enemies;

import data.Items;
import model.Character;
import model.Enemy;
import model.enums.CombatTag;
import model.enums.enemy.EnemyStats;
import model.enums.status.StatusEffects;
import model.enums.type.EnemyType;
import model.interfaces.SpectralAttacker;

public class Elf extends Enemy implements SpectralAttacker {

    private int chargeCounter = 0;

    public Elf(String name) {
        super(name,
                EnemyStats.ELF.getBaseMaxHp(),
                EnemyStats.ELF.getBaseAttack(),
                EnemyStats.ELF.getBaseDefence(),
                EnemyStats.ELF.getGoldReward(),
                EnemyStats.ELF.getXpReward(),
                Items.HEALTH_VIAL.getItem()
        );
    }
    public Elf(String name, int level) {
        super(name,
                EnemyStats.ELF.getBaseMaxHp(),
                EnemyStats.ELF.getBaseAttack(),
                EnemyStats.ELF.getBaseDefence(),
                EnemyStats.ELF.getGoldReward(),
                EnemyStats.ELF.getXpReward(),
                level,
                Items.HEALTH_VIAL.getItem()
        );
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
    public void performBasicAbility(Character target) {
        int damage = target.takeTrueDamage((int)(this.getTotalAttack() * 1.3));
        this.trueDamageAbilitySystemOut(damage, false);
    }

    @Override
    public void performSpecialAbility(Character target) {
        if (this.isStatusEffectsEmpty()) {
            this.applyStatusEffect(StatusEffects.ELF_STRENGTH, 3);
        } else if (this.chargeCounter <= 2) {
            this.print(this.getName() + " is charging a strong spell... " + (3 - this.chargeCounter) + " turns left!");
            this.chargeCounter++;
        } else {
            this.chargeCounter = 0;
            int damage = target.takeTrueDamage(this.getAttack() * 3);
            this.trueDamageAbilitySystemOut(damage, false);
        }
    }
}

