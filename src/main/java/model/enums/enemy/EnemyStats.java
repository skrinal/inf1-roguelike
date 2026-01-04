package model.enums.enemy;

//TODO: Fix values

public enum EnemyStats {
    SKELETON(100, 10, 5, 25, 120),
    TROLL(160, 13, 10, 50, 200),
    ELF(140, 16, 8, 150, 300),
    DRAGON(190, 20, 15, 200, 500),
    DEMON_LORD(500, 24, 19, 500, 1500);

    private final int maxHp;
    private final int attack;
    private final int defence;
    private final int goldReward;
    private final int xpReward;

    EnemyStats(int maxHp, int attack, int defence, int goldReward, int xpReward) {
        this.maxHp = maxHp;
        this.attack = attack;
        this.defence = defence;
        this.goldReward = goldReward;
        this.xpReward = xpReward;
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

    public int getGoldReward() {
        return this.goldReward;
    }

    public int getXpReward() {
        return this.xpReward;
    }


}
