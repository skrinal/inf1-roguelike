package model.enemies;

import model.Character;
import model.Enemy;
import model.enums.CombatTag;
import model.enums.status.EnemyType;

public class DemonLord extends Enemy {

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
    public boolean canTargetInvisible() {
        return true;
    }

    @Override
    public void performSpectralDamage(Character target) {
        int damage = target.takeDamage((this.getTotalAttack() * 40) / 100 ); // 40% normal Damage
        this.damageAbilitySystemOut(damage, true);
    }

    @Override
    public void performeBasicAbility(Character target) {
        int damage = target.takeDamage(this.getTotalAttack());
        this.damageAbilitySystemOut(damage, false);

    }

    @Override
    public void performeSpecialAbility(Character target) {

    }
}
