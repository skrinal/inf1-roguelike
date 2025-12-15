package model.enemies;

import model.Character;
import model.Enemy;
import model.enums.EnemyType;

public class DemonLord extends Enemy {

    private static final int MAX_HP = 5;
    private static final int ATTACK = 30;
    private static final int DEFENCE = 20;
    //private static final int GOLD_REWARD = 100;

    public DemonLord(String name,  int goldReward) {
        super(name, MAX_HP, ATTACK, DEFENCE, goldReward);
    }
    public DemonLord(String name,  int goldReward, int level) {
        super(name, MAX_HP, ATTACK, DEFENCE, goldReward, level);
    }

    // TODO: preco tu je string ?? IntelJ kuk -> zmenit potom UML
    @Override
    public String getEnemyType() {
        return EnemyType.DEMON.name();
    }

    @Override
    public boolean canTargetInvisible() {
        return false;
    }

    @Override
    public void performeBasicAbility(Character target) {

    }

    @Override
    public void performeSpecialAbility(Character target) {

    }
}
