package model.enemies;

import model.Enemy;

public class SkeletonWarrior extends Enemy {

    public SkeletonWarrior(String name, int maxHp, int attack, int defence, int goldReward) {
        super(name, maxHp, attack, defence, goldReward);
    }

    @Override
    public void performeAttack(model.Character target) {

    }

    @Override
    public void displayStats() {

    }

    @Override
    public void performSpecialAbility(model.Character target) {

    }
}
