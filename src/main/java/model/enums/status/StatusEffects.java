package model.enums.status;

public enum StatusEffects {
    STRENGTH("Strength +25% Damage"),
    INVISIBILITY("Invisible"),
    VANISH("Untargetable"),
    SHIELD("Shield +15% maxHP"),
    HEALING("Healing over time"),
    BLEEDING("Bleeding -2% maxHP per turn"),
    THORNS("Reflecting 5% damage"),

    // Skeleton
    SKELETON_CURSE("Losing 1% maxHP per turn"),

    // Troll

    // Elf
    ELF_STRENGTH("Strength +20% Damage"),
    // Dragon

    // DemonLord
    DEMONLORD_CURSE("-90% Damage reduction"),

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
