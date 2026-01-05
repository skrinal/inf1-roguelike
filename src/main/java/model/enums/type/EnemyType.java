package model.enums.type;

/**
 * EnemyType is an enum representing different types of enemies within the game.
 * Each enemy type defines specific characteristics such as whether the enemy is spectral
 * and the damage percentage it deals during spectral attacks.
 *
 * Enum values:
 * - TRASH: A basic enemy type with no spectral abilities and a low damage percentage.
 * - ELITE: A stronger enemy with spectral abilities and higher damage percentage.
 * - BOSS: The most powerful type of enemy with spectral abilities and the highest damage percentage.
 *
 * Methods:
 * - isSpectral: Returns whether the enemy type is capable of spectral attacks.
 * - getDamagePercentage: Retrieves the percentage of damage this enemy type deals.
 */
public enum EnemyType {
    TRASH(false, 0),
    ELITE(true, 30),
    BOSS(true, 50);

    private final boolean isSpectral;
    private final int damagePercentage;
    EnemyType(boolean isSpectral, int damagePercentage) {
        this.isSpectral = isSpectral;
        this.damagePercentage = damagePercentage;
    }

    public boolean isSpectral() {
        return this.isSpectral;
    }

    public int getDamagePercentage() {
        return this.damagePercentage;
    }
}
