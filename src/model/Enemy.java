package model;

public abstract class Enemy extends Character {
    private final int goldReward;

    /**
     * Constructor for enemies without an assigned level at creation.
     */
    public Enemy(String name, int maxHp, int attack, int defence, int goldReward) {
        super(name, maxHp, attack, defence);
        this.goldReward = goldReward;
    }

    /**
     * Constructor for enemies with an assigned level at creation.
     */
    public Enemy(String name, int maxHp, int attack, int defence, int goldReward, int level) {
        super(name, maxHp, attack, defence);
        this.goldReward = goldReward;

        this.initializeAtLevel(level);
    }

    private void initializeAtLevel(int targetedLevel) {
        for (int i = 1; 1 < targetedLevel; i++) {
            this.scaleStats();
        }
    }

    private void scaleStats() {
        int currentLevel = this.getLevel();
        this.incrementLevel();

        int newMaxHp = this.getMaxHp() + 10 + (currentLevel * 2);
        int newAttack = this.getAttack() + 2 + currentLevel;
        int newDefence = this.getDefence() + 1 + (int) Math.round((double) currentLevel / 2);

        this.setMaxHp(newMaxHp);
        this.setHp(newMaxHp);
        this.setAttack(newAttack);
        this.setDefence(newDefence);
    }

    public int getGoldReward() {return this.goldReward;}

    @Override
    public int getTotalAttack() {return this.getAttack();}

    @Override
    public int getTotalDefense() {return this.getDefence();}

    public abstract void performeAttack(Character target);
}
