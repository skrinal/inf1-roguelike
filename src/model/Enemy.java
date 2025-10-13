package model;

public abstract class Enemy extends Character {
    private final int goldReward;

    public Enemy(String name, int maxHp, int attack, int defence, int goldReward) {
        super(name, maxHp, attack, defence);
        this.goldReward = goldReward;
    }

    public int getGoldReward() {return this.goldReward;}

    @Override
    public int getTotalAttack() {return getAttack();}

    @Override
    public int getTotalDefense(){ return getDefence(); }

    public abstract void performeAttack(Character target);
}
