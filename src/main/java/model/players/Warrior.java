package model.players;

import model.Character;
import model.Player;
import model.enums.ClassPower;
import model.enums.CombatTag;
import model.enums.players.PlayerStats;
import model.enums.status.StatusEffects;
import utility.Utility;

/**
 * The Warrior class represents a warrior-type player in the game. It extends the Player class,
 * providing unique abilities, stances, and behaviors specific to the Warrior archetype.
 * Warriors utilize rage power to execute their abilities and manage their stances strategically
 * during combat.
 *
 * Key features of the Warrior include:
 *  * - Basic Ability: Bloodthirst, which is the main kit of his demage.
 *  * - Special Ability: Execute, a really powerful ability if the target is under 15% HP.
 *  * - Utility Ability: War Stance, makes warior change pre-defined stances.
 */
public class Warrior extends Player {

    private final String basicAbilityName = "Bloodthirst";
    private final String specialAbilityName = "Execute";
    private final String utilityAbilityName = "War Stance";

    private final String actionVerb = "slash";

    private final int basicAbilityCost = 20;
    private final int specialAbilityCost = 30;
    private final int utilityAbilityCost = 10;

    private final double basicAbilityMultiplayer = 1.4;
    private final double specialAbilityMultiplayer = 0.4;

    private StatusEffects stance;

    /**
     * Constructs a new Warrior instance with the specified name, initializing its
     * attributes based on the predefined Warrior class statistics.
     *
     * @param name the name of the Warrior to be created
     */
    public Warrior(String name) {
        super(name,
                PlayerStats.WARRIOR.getBaseMaxHp(),
                PlayerStats.WARRIOR.getBaseAttack(),
                PlayerStats.WARRIOR.getBaseDefence(),
                PlayerStats.WARRIOR.getBasePower(),
                1
        );
        this.initializeStance();
    }

    /**
     * Constructs a new Warrior instance with the specified name and level, initializing its
     * attributes based on the predefined Warrior class statistics.
     *
     * @param name the name of the Warrior to be created
     * @param level the starting level of the Warrior
     */
    public Warrior(String name, int level) {
        super(name,
                PlayerStats.WARRIOR.getBaseMaxHp(),
                PlayerStats.WARRIOR.getBaseAttack(),
                PlayerStats.WARRIOR.getBaseDefence(),
                PlayerStats.WARRIOR.getBasePower(),
                level
        );
        this.initializeStance();
    }

    /**
     * Returns the string representation of the Warrior's power type.
     */
    @Override
    public String getPowerString() {
        return ClassPower.RAGE.toString();
    }

    /**
     * Returns the combat tag associated with the Warrior class.
     */
    @Override
    public CombatTag getCombatTag() {
        return CombatTag.WARRIOR;
    }

    /**
     * Returns the name of the Warrior's basic ability. (Mainly for testing purposes.)
     */
    @Override
    public String getBasicAbilityName() {
        return this.basicAbilityName;
    }

    /**
     * Returns the name of the Warrior's special ability. (Mainly for testing purposes.)
     */
    @Override
    public String getSpecialAbilityName() {
        return this.specialAbilityName;
    }

    /**
     * Returns the name of the Warrior's utility ability. (Mainly for testing purposes.)
     */
    @Override
    public String getUtilityAbilityName() {
        return this.utilityAbilityName;
    }

    /**
     * Returns the cost of the Warrior's basic ability. (Mainly for testing purposes.)
     */
    @Override
    public int getBasicAbilityCost() {
        return this.basicAbilityCost;
    }

    /**
     * Returns the cost of the Warrior's special ability. (Mainly for testing purposes.)
     */
    @Override
    public int getSpecialAbilityCost() {
        return this.specialAbilityCost;
    }

    /**
     * Returns the cost of the Warrior's utility ability. (Mainly for testing purposes.)
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
        switch (this.stance) {
            case DEFENSIVE -> this.restorePower(15);
            case AGGRESSIVE -> this.restorePower(5);
            case BALANCED -> this.restorePower(10);
            default -> {

            }
        }
    }

    /**
     * Executes the basic ability of the Warrior on a specific target.
     * Simple check if enough power is available to cast the ability.
     * Target takes damage. Output to console.
     * If there is not enough power, informs the player.
     */
    @Override
    public void performBasicAbility(Character target) {
        if (usePower(this.basicAbilityCost)) {
            int damage = (int)(this.getTotalAttack() * this.basicAbilityMultiplayer);
            int rawDamage = target.takeDamage(damage, this);

            this.damageAbilitySystemOut(
                    this.basicAbilityName, this.actionVerb, target, damage, rawDamage
            );
        } else {
            this.noPowerSystemOut(this.getPowerString());
        }
    }

    /**
     * Executes the special ability of the Warrior on a specific target. The ability deals significant damage,
     * executing the target instantly if their health is below 15% or otherwise inflicting damage based on the Warrior's
     * attributes. The execution of this ability depends on the availability of sufficient power.
     */
    @Override
    public void performSpecialAbility(Character target) {
        if (usePower(this.specialAbilityCost)) {
            if (this.isBelow15Percent(target.getHp(), target.getMaxHp())) {
                target.takeTrueDamage(Integer.MAX_VALUE);

                this.print(target.getName() + " has been executed");

            } else {
                int damage = (int)(this.getTotalAttack() * this.specialAbilityMultiplayer);
                int rawDamage = target.takeDamage(damage, this);

                this.print(target.getName() + " is not under 15% HP");
                this.damageAbilitySystemOut(
                        this.specialAbilityName, this.actionVerb, target, damage, rawDamage
                );
            }
        } else {
            this.noPowerSystemOut(this.getPowerString());
        }
    }

    private boolean isBelow15Percent(int enemyHp, int enemyMaxHp) {
        return enemyHp <= (enemyMaxHp * 0.15);
    }

    /**
     * Executes the utility ability for the Warrior, allowing the player to select a new stance:
     * Aggressive, Defensive, or Balanced. The ability costs a certain amount of power to execute.
     *
     * If the player has sufficient power, the method provides available stance options and prompts the player
     * to select one. The selected stance is then applied, and a confirmation message is displayed. If the player
     * lacks sufficient power, a message indicating insufficient power is displayed instead.
     */
    @Override
    public void performeUtilityAbility() {
        if (usePower(this.utilityAbilityCost)) {
            this.print("Current stance: " + this.stance);
            this.print("Choose a stance:");
            this.print("1. Aggressive");
            this.print("2. Defensive");
            this.print("3. Balanced");

            int choice = Utility.handleDecision(1, 3);
            switch (choice) {
                case 1 -> this.setStance(StatusEffects.AGGRESSIVE);
                case 2 -> this.setStance(StatusEffects.DEFENSIVE);
                case 3 -> this.setStance(StatusEffects.BALANCED);
            }
            this.useAbilitySystemOut(this.utilityAbilityName);
        } else {
            this.noPowerSystemOut(this.getPowerString());
        }
    }

    private void setStance(StatusEffects stance) {
        StatusEffects previousStance = this.stance;
        this.stance = stance;
        if (previousStance == this.stance) {
            return;
        }

        this.applyStatusEffect(stance, -1);
        this.removeStatusEffect(previousStance);

        this.print("Stance changed to " + this.stance);
    }

    private void initializeStance() {
        this.stance = StatusEffects.BALANCED;
        this.applyStatusEffect(this.stance, -1);
    }
}
