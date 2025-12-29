package model.enemies;

import model.Character;
import model.Enemy;
import model.enums.CombatTag;
import model.enums.type.EnemyType;
import model.interfaces.SpectralAttacker;

public class Dragon extends Enemy implements SpectralAttacker {
    //TODO change values
    private static final int MAX_HP = 5;
    private static final int ATTACK = 30;
    private static final int DEFENCE = 20;
    private static final int GOLD_REWARD = 100;

    public Dragon(String name) {
        super(name, MAX_HP, ATTACK, DEFENCE, GOLD_REWARD);
    }
    public Dragon(String name, int level) {
        super(name, MAX_HP, ATTACK, DEFENCE, GOLD_REWARD, level);
    }

    @Override
    public EnemyType getEnemyType() {
        return EnemyType.ELITE;
    }

    @Override
    public CombatTag getCombatTag() {
        return CombatTag.DRAGON;
    }

    @Override
    public void performeBasicAbility(Character target) {

    }

    @Override
    public void performeSpecialAbility(Character target) {

    }
}
