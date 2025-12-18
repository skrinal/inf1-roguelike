package model.enums.status;

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
