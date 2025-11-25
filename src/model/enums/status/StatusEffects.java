package model.enums.status;

public enum StatusEffects {
    STRENGTH("Strength +25% Damage"),
    INVISIBILITY("Invisible"),
    SHIELD("Shield +50% Defence"),
    HEALING("Healing over time");

    private final String description;

    StatusEffects(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
