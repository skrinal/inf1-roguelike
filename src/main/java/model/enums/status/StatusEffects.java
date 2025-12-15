package model.enums.status;

public enum StatusEffects {
    STRENGTH("Strength +25% Damage"),
    INVISIBILITY("Invisible"),
    VANISH("Untargetable"),
    SHIELD("Shield +15% HP"),
    HEALING("Healing over time"),
    BLEEDING("Bleeding -2% HP per turn"),

    // Warrior Stances as Buff
    AGGRESSIVE("+20% Damage"),
    BALANCED("-50% Ability cost"),
    DEFENSIVE("+20% Defence, Thorn Damage");

    private final String description;

    StatusEffects(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
