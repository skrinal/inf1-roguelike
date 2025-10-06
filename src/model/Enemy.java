package model;

public abstract class Enemy extends Character {
    protected int goldReward;

    public Enemy(String name, int maxHp, int attack, int defence, int goldReward) {
        super(name, maxHp, attack, defence);
        this.goldReward = goldReward;
    }

    public int getGoldReward() {return goldReward;}

    @Override
    public int getTotalAttack() {return attack;}

    @Override
    public int getTotalDefense(){ return defence; }

    public abstract void performeAttack(Character target);
}
