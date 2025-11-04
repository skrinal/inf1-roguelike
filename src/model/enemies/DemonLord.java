package model.enemies;

import model.Character;
import model.Enemy;
import model.enums.EnemyType;

public class DemonLord extends Enemy {

    private static final int MAX_HP = 100;
    private static final int ATTACK = 100;
    private static final int DEFENCE = 100;
    //private static final int GOLD_REWARD = 100;

    public DemonLord(String name,  int goldReward) {
        super(name, MAX_HP, ATTACK, DEFENCE, goldReward);
    }
    public DemonLord(String name,  int goldReward, int level) {
        super(name, MAX_HP, ATTACK, DEFENCE, goldReward, level);
    }

    @Override
    public void performeAttack(Character target) {

    }

    @Override
    public String getEnemyType() {
        return EnemyType.DEMON.name();
    }


    @Override
    public void performSpecialAbility(Character target) {

    }
}
