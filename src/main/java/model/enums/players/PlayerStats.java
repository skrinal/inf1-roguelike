package model.enums.players;

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
