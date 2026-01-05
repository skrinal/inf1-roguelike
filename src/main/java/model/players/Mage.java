package model.players;

import model.Character;
import model.Player;
import model.enums.ClassPower;
import model.enums.CombatTag;
import model.enums.players.PlayerStats;
import model.enums.status.StatusEffects;

/**
 * Represents a Mage, a playable character class specialized in magic attacks
 * and utility abilities. The Mage uses mana as its primary power resource and
 * has access to abilities that balance offense and defense.
 *
 * Key features of the Mage include:
 * - Basic Ability: Frostbolt, which deals damage at a relatively low power cost.
 * - Special Ability: Fireblast, a more powerful damage-dealing ability with higher power cost.
 * - Utility Ability: Cloak of Shadows, which enables temporary invisibility to evade attacks.
 *
 * The Mage has a unique mechanic for invisibility, granting temporary avoidance.
 * Its abilities scale with the Mage's attack, and the class focuses on effective
 * damage dealing and tactical utility.
 */
public class Mage extends Player {

    private final String basicAbilityName = "Frostbolt";
    private final String specialAbilityName = "Fireblast";
    private final String utilityAbilityName = "Cloak of Shadows";

    private final int basicAbilityCost = 10;
    private final int specialAbilityCost = 50;
    private final int utilityAbilityCost = 15;

    private final double basicAbilityMultiplayer = 1.3;
    private final double specialAbilityMultiplayer = 2.5;

    private final int invisibilityDuration = -1;

    private final String actionVerb = "blast";

    private int invisibilityTurns = 0;
    private double invisibilityChance = 0.4;
    private boolean isInvisible = false;

    /**
     * Constructs a new Mage instance with the specified name.
     * Initializes the Mage with predefined stats for maximum health, attack, defense,
     * power, and sets the level to 1.
     */
    public Mage(String name) {
        super(name,
                PlayerStats.MAGE.getBaseMaxHp(),
                PlayerStats.MAGE.getBaseAttack(),
                PlayerStats.MAGE.getBaseDefence(),
                PlayerStats.MAGE.getBasePower(),
                1
        );
    }

    /**
     * Constructs a new Mage instance with the specified name and level.
     * Initializes the Mage with predefined stats for maximum health, attack, defense, and power,
     * based on the Mage's base attributes.
     */
    public Mage(String name, int level) {
        super(name,
                PlayerStats.MAGE.getBaseMaxHp(),
                PlayerStats.MAGE.getBaseAttack(),
                PlayerStats.MAGE.getBaseDefence(),
                PlayerStats.MAGE.getBasePower(),
                level
        );
    }

    /**
     * Returns the string representation of the Mage's power type.
     */
    @Override
    public String getPowerString() {
        return ClassPower.MANA.name();
    }

    /**
     * Returns the combat tag associated with the Mage class.
     */
    @Override
    public CombatTag getCombatTag() {
        return CombatTag.MAGE;
    }

    /**
     * Returns the name of the Mage's basic ability. (Mainly for testing purposes.)
     */
    @Override
    public String getBasicAbilityName() {
        return this.basicAbilityName;
    }

    /**
     * Returns the name of the Mage's basic ability. (Mainly for testing purposes.)
     */
    @Override
    public String getSpecialAbilityName() {
        return this.specialAbilityName;
    }

    /**
     * Returns the name of the Mage's utility ability. (Mainly for testing purposes.)
     */
    @Override
    public String getUtilityAbilityName() {
        return this.utilityAbilityName;
    }

    /**
     * Returns the cost of the Mage's basic ability. (Mainly for testing purposes.)
     */
    @Override
    public int getBasicAbilityCost() {
        return this.basicAbilityCost;
    }

    /**
     * Returns the cost of the Mage's special ability. (Mainly for testing purposes.)
     */
    @Override
    public int getSpecialAbilityCost() {
        return this.specialAbilityCost;
    }

    /**
     * Returns the cost of the Mage's utility ability. (Mainly for testing purposes.)
     */
    @Override
    public int getUtilityAbilityCost() {
        return this.utilityAbilityCost;
    }

    /**
     * Restores the player's power based on their current stance.
     */
    @Override
    public void beforeTurn() {
        if (this.isInvisible) {
            this.restorePower(5);
        } else {
            this.restorePower(15);
        }
    }

    /**
     * Executes the basic ability of the Mage on a specific target.
     * Simple check if enough power is available to cast the ability.
     * Target takes damage. Output to console.
     * If there is not enough power, informs the player.
     */
    @Override
    public void performBasicAbility(Character target) {
        if (usePower(this.basicAbilityCost)) {
            int rawDamage = (int)(this.getTotalAttack() * this.basicAbilityMultiplayer);
            int damage = target.takeDamage(rawDamage, this);

            this.damageAbilitySystemOut(
                    this.basicAbilityName, this.actionVerb, target, damage, rawDamage
            );

        } else {
            this.noPowerSystemOut(this.getPowerString());
        }
    }

    /**
     * Executes the special ability of the Mage on a specific target. Checks if enough
     * power is available to perform the ability. If successful, the target takes
     * damage based on the Mage's total attack and special ability multiplier. Outputs
     * the result to the console. If there is not enough power, informs the player.
     */
    @Override
    public void performSpecialAbility(Character target) {
        if (usePower(this.specialAbilityCost)) {
            int rawDamage = (int)(this.getTotalAttack() * this.specialAbilityMultiplayer);
            int damage = target.takeDamage(rawDamage, this);

            this.damageAbilitySystemOut(
                    this.specialAbilityName, this.actionVerb, target, damage, rawDamage
            );

        } else {
            this.noPowerSystemOut(this.getPowerString());
        }
    }

    /**
     * Executes the utility ability of the Mage class.
     * This ability allows the Mage to turn invisible for a specified duration if certain conditions are met.
     *
     * Behavior:
     * 1. Checks if the Mage is already invisible. If invisible, outputs a message indicating this state.
     * 2. If not already invisible:
     *     Validates if the Mage has enough power to activate the utility ability.
     *       If enough power is available:
     *       - Deducts the required power cost for the utility ability.
     *       - Applies the invisibility status effect to the Mage for a predefined duration.
     *       - Outputs a message indicating that the ability has been successfully cast.
     *       If not enough power:w
     *       - Outputs a message informing the player of the lack of power.
     *
     * No actions are taken if the Mage is already invisible.
     */
    @Override
    public void performeUtilityAbility() {
        if (!this.isInvisible) {
            if (this.usePower(this.utilityAbilityCost)) {

                this.isInvisible = true;
                this.applyStatusEffect(StatusEffects.INVISIBILITY, this.invisibilityDuration);

                this.useAbilitySystemOut(this.utilityAbilityName, "casted");
            } else {
                this.noPowerSystemOut(this.getPowerString());
            }
        } else {
            this.print("You are already invisible!");
        }
    }

    /**
     * Resets the invisibility variables to their initial values.
     */
    public void resetInvisibility() {
        this.invisibilityTurns = 0;
        this.isInvisible = false;
        this.invisibilityChance = 0.6;
    }

    /**
     * Decreases the invisibility chance by 0.1 every turn.
     */
    public void decreaseInvisibilityChance() {
        this.invisibilityChance -= 0.1;
    }

    /**
     * Returns the number of turns the Mage has been invisible.
     */
    public double getInvisibilityChance() {
        return this.invisibilityChance;
    }
}
