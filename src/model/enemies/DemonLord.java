package model.enemies;

import model.Character;
import model.Enemy;

public class DemonLord extends Enemy {
    public DemonLord(String name, int maxHp, int attack, int defence, int goldReward) {
        super(name, maxHp, attack, defence, goldReward);
    }
    public DemonLord(String name, int maxHp, int attack, int defence, int goldReward, int level) {
        super(name, maxHp, attack, defence, goldReward, level);
    }


    @Override
    public void performeAttack(Character target) {

    }

    @Override
    public void displayStats() {

    }

    @Override
    public void performSpecialAbility(Character target) {

    }
}
