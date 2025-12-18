package model.enemies;

import model.Character;
import model.Enemy;
import model.enums.CombatTag;
import model.enums.status.EnemyType;
import model.interfaces.SpectralAttacker;

public class DemonLord extends Enemy implements SpectralAttacker {

    private static final int MAX_HP = 5;
    private static final int ATTACK = 30;
    private static final int DEFENCE = 20;
    private static final int GOLD_REWARD = 100;

    public DemonLord(String name) {
        super(name, MAX_HP, ATTACK, DEFENCE, GOLD_REWARD);
    }
    public DemonLord(String name, int level) {
        super(name, MAX_HP, ATTACK, DEFENCE, GOLD_REWARD, level);
    }

    // TODO: preco tu je string ?? IntelJ kuk -> zmenit potom UML
    @Override
    public CombatTag getCombatTag() {
        return CombatTag.DEMON;
    }

    @Override
    public EnemyType getEnemyType() {
        return EnemyType.BOSS;
    }

    @Override
    public void performeBasicAbility(Character target) {
        int damage = target.takeDamage(this.getTotalAttack());
        this.damageAbilitySystemOut(damage, false);

    }

    @Override
    public void performeSpecialAbility(Character target) {

    }

    @Override
    public void performSpectralDamage(Character target) {
        int damage = target.takeDamage((this.getTotalAttack() * this.getEnemyType().getDamagePercentage()) / 100 ); // 50% normal Damage
        this.damageAbilitySystemOut(damage, true);
    }
}
