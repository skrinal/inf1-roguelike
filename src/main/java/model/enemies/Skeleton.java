package model.enemies;

import model.Character;
import model.Enemy;
import model.enums.CombatTag;

public class Skeleton extends Enemy {
    // TODO: opravit
    private static final int MAX_HP = 5;
    private static final int ATTACK = 8;
    private static final int DEFENCE = 2;
    //private static final int GOLD_REWARD = 100;

    public Skeleton(String name, int goldReward) {
        super(name, MAX_HP, ATTACK, DEFENCE, goldReward);
    }

    public Skeleton(String name, int goldReward, int level) {
        super(name, MAX_HP, ATTACK, DEFENCE, goldReward, level);
    }

    @Override
    public CombatTag getCombatTag() {
        return CombatTag.SKELETON;
    }

    @Override
    public boolean canTargetInvisible() {
        return false;
    }

    @Override
    public void performSpectralDamage(Character target) {

    }

    @Override
    public void performeBasicAbility(Character target) {
        //int xd = switch (target.)
    }

    @Override
    public void performeSpecialAbility(Character target) {

    }
}
