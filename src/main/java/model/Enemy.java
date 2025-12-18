package model;

import model.enums.CombatTag;
import model.enums.status.EnemyType;
import utility.Utility;

public abstract class Enemy extends Character {
    private int goldReward;

    /**
     * Constructor for enemy without an assigned level at creation.
     */
    protected Enemy(String name, int maxHp, int attack, int defence, int goldReward) {
        super(name, maxHp, attack, defence);
        this.goldReward = goldReward;
    }

    /**
     * Constructor for enemy with an assigned level at creation.
     */
    protected Enemy(String name, int maxHp, int attack, int defence, int goldReward, int level) {
        super(name, maxHp, attack, defence);
        this.goldReward = goldReward;

        this.initializeAtLevel(level);
    }

    private void initializeAtLevel(int targetedLevel) {
        for (int i = 1; i < targetedLevel; i++) {
            this.scaleStats();
        }
    }

    private void scaleStats() {
        int currentLevel = this.getLevel();
        this.incrementLevel();

        int newMaxHp = this.getMaxHp() + 15 + (currentLevel * 2);
        int newAttack = this.getAttack() + 2 + currentLevel;
        int newDefence = this.getDefence() + 2 + (int)Math.round((double)currentLevel / 2);
        int newGold = this.goldReward + (currentLevel * 10);

        this.setMaxHp(newMaxHp);
        this.setHp(newMaxHp);
        this.setAttack(newAttack);
        this.setDefence(newDefence);
        this.goldReward = newGold;
    }

    public int getGoldReward() {
        return this.goldReward;
    }

    @Override
    public int getTotalAttack() {
        return this.getAttack();
    }

    @Override
    public int getTotalDefense() {
        return this.getDefence();
    }

    protected void damageAbilitySystemOut(int damage, boolean isSpectral) {
        StringBuilder sb = new StringBuilder(80);
        sb.append(this.returnFormattedText(isSpectral))
                .append(damage);
        System.out.println(sb);

        Utility.enterToContinue();
    }

    private String returnFormattedText(boolean isSpectral) {
        if (isSpectral) {
             return switch (this.getEnemyType()) {
                case BOSS -> "The mighty " + this.getCombatTag().name() + " can still see you and It's dealing ";
                case ELITE -> "The " + this.getCombatTag().name() + " sees you and It's dealing ";
                default -> "";
            };
        }

        return switch (this.getEnemyType()) {
            case BOSS -> "The mighty " + this.getCombatTag().name() + " Boss struck you for ";
            case ELITE -> "The " + this.getCombatTag().name() + " Elite struck you for ";
            case TRASH -> "A wild " + this.getCombatTag().name() + " mob hits you for ";
        };
    }

    public boolean canTargetInvisible() {
        return this.getEnemyType().isSpectral();
    }


    public abstract EnemyType getEnemyType();
    //public abstract void performSpectralDamage(Character target);
}
