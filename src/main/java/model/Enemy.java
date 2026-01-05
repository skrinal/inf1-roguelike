package model;

import model.enums.type.EnemyType;

/**
 * Represents an abstract Enemy character in the game.
 * Enemies are hostile entities that can battle with the player or other characters.
 * This base class provides the shared attributes and methods essential for defining specific enemy
 * types, as well as common behavior for all enemy instances.
 */
public abstract class Enemy extends Character {
    private int goldReward;
    private int xpReward;
    private final Item consumableDrop;

    /**
     * Constructor for enemy without an assigned level at creation.
     */
    protected Enemy(String name, int maxHp, int attack, int defence, int goldReward, int baseXpReward, Item consumableDrop) {
        super(name, maxHp, attack, defence, 1);
        this.goldReward = goldReward;
        this.xpReward = baseXpReward;
        this.consumableDrop = consumableDrop;
    }

    /**
     * Constructor for enemy with an assigned level at creation.
     */
    protected Enemy(String name, int maxHp, int attack, int defence, int goldReward, int baseXpReward, int level, Item consumableDrop) {
        super(name, maxHp, attack, defence, level);
        this.goldReward = goldReward;
        this.xpReward = baseXpReward;
        this.consumableDrop = consumableDrop;
    }

    /**
     * Returns the consumable drop from Enemy.
     * Each instance of enemy has different consumable drop.
     */
    public Item getConsumableDrop() {
        return this.consumableDrop;
    }

    /**
     * Just a simple method for outputting a miss-hit message.
     * Not needed but better for readability. Possible to upgrade with a different text.
     */
    public void missHit() {
        this.print("Enemy missed!");
    }

    /**
     * Returns a number of XP reward for this enemy.
     */
    public int getXpReward() {
        return this.xpReward;
    }

    protected void setXpReward(int xpReward) {
        this.xpReward = xpReward;
    }

    /**
     * Returns a number of Gold reward for this enemy.
     */
    public int getGoldReward() {
        return this.goldReward;
    }

    protected void setGoldReward(int goldReward) {
        this.goldReward = goldReward;
    }

    /**
     * Return this enemy Attack without any items.
     * Right now enemies have no items, so this is just a simple getter.
     */
    @Override
    public int getTotalAttack() {
        return this.getAttack();
    }

    /**
     * Return this enemy Defense without any items.
     * Right now enemies have no items, so this is just a simple getter.
     */
    @Override
    public int getTotalDefense() {
        return this.getDefence();
    }

    /**
     * Outputs a formatted message to notify damage dealt by an enemy, followed by a pause in the system.
     * The message format depends on whether the attack is spectral and the enemy type.
     */
    public void damageAbilitySystemOut(int damage, boolean isSpectral) {
        String sb = this.returnFormattedText(isSpectral)
                + damage
                + " damage";

        this.print(sb);

        this.pause();
    }

    /**
     * Outputs a formatted message to notify damage dealt by an enemy, followed by a pause in the system.
     * The message format depends on whether the attack is spectral and the enemy type.
     */
    public void trueDamageAbilitySystemOut(int damage, boolean isSpectral) {
        String sb = this.returnFormattedText(isSpectral)
                + damage
                + " true damage";

        this.print(sb);

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

    /**
     * Returns if the enemy can target invisible characters.
     */
    public boolean canTargetInvisible() {
        return this.getEnemyType().isSpectral();
    }

    /**
     * Return enemyType
     */
    public abstract EnemyType getEnemyType();
}
