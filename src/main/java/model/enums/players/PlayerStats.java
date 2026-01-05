package model.enums.players;

/**
 * An enum that defines the base statistics for various player classes.
 * Each player class is defined with specific values for maximum health points (maxHp),
 * attack, defense, and power.
 */
public enum PlayerStats {
    ROGUE(100, 8, 5, 150),
    WARRIOR(100, 7, 6, 120),
    MAGE(80, 10, 3, 100);

    private final int maxHp;
    private final int attack;
    private final int defence;
    private final int power;

    PlayerStats(int maxHp, int attack, int defence, int power) {
        this.maxHp = maxHp;
        this.attack = attack;
        this.defence = defence;
        this.power = power;
    }

    public int getBaseMaxHp() {
        return this.maxHp;
    }

    public int getBaseAttack() {
        return this.attack;
    }

    public int getBaseDefence() {
        return this.defence;
    }

    public int getBasePower() {
        return this.power;
    }
}
