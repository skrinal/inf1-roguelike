package model;

import model.enums.type.EnemyType;

public abstract class Enemy extends Character {
    private int goldReward;

    /**
     * Constructor for enemy without an assigned level at creation.
     */
    protected Enemy(String name, int maxHp, int attack, int defence, int goldReward) {
        super(name, maxHp, attack, defence, 1);
        this.goldReward = goldReward;
    }

    /**
     * Constructor for enemy with an assigned level at creation.
     */
    protected Enemy(String name, int maxHp, int attack, int defence, int goldReward, int level) {
        super(name, maxHp, attack, defence, level);
        this.goldReward = goldReward;
    }

    public int getGoldReward() {
        return this.goldReward;
    }

    protected void setGoldReward(int goldReward) {
        this.goldReward = goldReward;
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
                .append(damage)
                .append(" damage");

        this.print(sb.toString());

        this.pause();
    }

    protected void trueDamageAbilitySystemOut(int damage, boolean isSpectral) {
        StringBuilder sb = new StringBuilder(80);
        sb.append(this.returnFormattedText(isSpectral))
                .append(damage)
                .append(" true damage");

        this.print(sb.toString());

        this.pause();
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
}
