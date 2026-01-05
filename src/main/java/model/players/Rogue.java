package model.players;

import model.Character;
import model.Player;
import model.enums.ClassPower;
import model.enums.CombatTag;
import model.enums.players.PlayerStats;
import model.enums.status.StatusEffects;

import java.util.Random;

/**
 * Represents a Rogue class in the game that extends the Player superclass.
 * The Rogue specializes in stealth-based combat with unique abilities that
 * focus on vanishing, backstabbing, and strategic dice rolling to control the
 * flow of battle. Implements specific abilities like Sinister Strike, Vanish,
 * and Dice Roll to enhance its combat role.
 *
 * Key features of the Rogue include:
 *  * - Basic Ability: Sinister Strike, deals the main damage.
 *  * - Special Ability: Vanish, a more powerful damage-dealing ability with higher power cost.
 *  * - Utility Ability: Dice roll, possible to get a buff depending on the dice roll.
 */
public class Rogue extends Player {

    private final String basicAbilityName = "Sinister Strike";
    private final String specialAbilityName = "Vanish";
    private final String utilityAbilityName = "Dice roll";

    private final int basicAbilityCost = 20;
    private final int specialAbilityCost = 60;
    private final int utilityAbilityCost = 10;

    private final double basicAbilityMultiplayer = 1.5;
    private final double basicAbilityBackStabMultiplier = 2.2;
    private final double specialAbilityDamageMultiplier = 2.9;

    private final String actionVerb = "stab";

    private double vanishChance = 0.6;
    private int vanishTurns = 0;
    private boolean isVanished = false;

    private final Random random = new Random();

    /**
     * Constructs a new Rogue object with the specified name.
     * The Rogue character is initialized with predefined base stats,
     * including maximum health, attack, defense, power, and starting level.
     */
    public Rogue(String name) {
        super(name,
                PlayerStats.ROGUE.getBaseMaxHp(),
                PlayerStats.ROGUE.getBaseAttack(),
                PlayerStats.ROGUE.getBaseDefence(),
                PlayerStats.ROGUE.getBasePower(),
                1
        );
    }

    /**w
     * Constructs a new Rogue object with the specified name and level.
     * The Rogue character is initialized with predefined base statistics,
     * including maximum health, attack, defense, power, and starting level.
     */
    public Rogue(String name, int level) {
        super(name,
                PlayerStats.ROGUE.getBaseMaxHp(),
                PlayerStats.ROGUE.getBaseAttack(),
                PlayerStats.ROGUE.getBaseDefence(),
                PlayerStats.ROGUE.getBasePower(),
                level
        );
    }

    /**
     * Returns the string representation of the Rogue's power type.
     */
    @Override
    public String getPowerString() {
        return ClassPower.ENERGY.name();
    }

    /**
     * Returns the combat tag associated with the Rogue class.
     */
    @Override
    public CombatTag getCombatTag() {
        return CombatTag.ROGUE;
    }

    /**
     * Returns the name of the Rogue's basic ability. (Mainly for testing purposes.)
     */
    @Override
    public String getBasicAbilityName() {
        return this.basicAbilityName;
    }

    /**
     * Returns the name of the Rogue's special ability. (Mainly for testing purposes.)
     */
    @Override
    public String getSpecialAbilityName() {
        return this.specialAbilityName;
    }

    /**
     * Returns the name of the Rogue's utility ability. (Mainly for testing purposes.)
     */
    @Override
    public String getUtilityAbilityName() {
        return this.utilityAbilityName;
    }

    /**
     * Returns the cost of the Rogue's basic ability. (Mainly for testing purposes.)
     */
    @Override
    public int getBasicAbilityCost() {
        return this.basicAbilityCost;
    }

    /**
     * Returns the cost of the Rogue's special ability. (Mainly for testing purposes.)
     */
    @Override
    public int getSpecialAbilityCost() {
        return this.specialAbilityCost;
    }

    /**
     * Returns the cost of the Rogue's utility ability. (Mainly for testing purposes.)
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
        if (this.isVanished) {
            this.restorePower(5);
        } else {
            this.restorePower(10);
        }
    }

    /**
     * Executes the basic ability of the Rogue on a specific target.
     * Simple check if enough power is available to cast the ability.
     * Target takes damage. Output to console.
     * If there is not enough power, informs the player.
     *
     * If a player is vanished and hits 50% change gets higher damage multiplayer.
     */
    @Override
    public void performBasicAbility(Character target) {
        if (this.isVanished && this.random.nextBoolean()) {
            if (usePower(this.basicAbilityCost)) {
                int rawDamage = (int)(this.getTotalAttack() * this.basicAbilityBackStabMultiplier);
                int actualDamage = target.takeDamage(rawDamage, this);

                this.damageAbilitySystemOut(
                        this.basicAbilityName, "back stabed", target, actualDamage, rawDamage
                );

            } else {
                this.noPowerSystemOut(this.getPowerString());
            }
            return;
        }

        if (usePower(this.basicAbilityCost)) {
            int rawDamage = (int)(this.getTotalAttack() * this.basicAbilityMultiplayer);
            int actualDamage = target.takeDamage(rawDamage, this);

            this.damageAbilitySystemOut(
                    this.basicAbilityName, this.actionVerb, target, actualDamage, rawDamage
            );

        } else {
            this.noPowerSystemOut(this.getPowerString());
        }
    }

    /**
     * Executes the special ability of the Rogue on a specific target.
     * If the Rogue is not already vanished, attempts to use the special ability by removing the required power.
     * When successfully executed, applies the VANISH status effect, marks the Rogue as vanished,
     * and inflicts true damage on the target with a damage multiplier.
     * If there is insufficient power to perform the ability, outputs a message to inform the player.
     * If the Rogue is already vanished, does not execute the ability and notifies the player.
     */
    @Override
    public void performSpecialAbility(Character target) {
        if (!this.isVanished) {
            if (this.usePower(this.specialAbilityCost)) {

                this.applyStatusEffect(StatusEffects.VANISH, -1);
                this.useAbilitySystemOut(this.specialAbilityName);

                this.isVanished = true;

                int rawDamage = (int)(this.getTotalAttack() * this.specialAbilityDamageMultiplier);
                int actualDamage = target.takeTrueDamage(rawDamage);

                this.damageAbilitySystemOut(
                        this.basicAbilityName, "back stabed", target, actualDamage, rawDamage
                );

            } else {
                this.noPowerSystemOut(this.getPowerString());
            }
        } else {
            this.print("You are already vanished!");
        }
    }

    /**
     * Executes the Rogue's utility ability.
     * This ability involves a dice roll that determines the outcome of the action.
     * If the Rogue has enough power to perform the ability, power is consumed,
     * and the outcome is based on a randomly generated number between 1 and 6:
     *
     * Roll outcomes:
     * - On 1, 3, or 5: Considered a bad roll. Outputs the result and pauses the game.
     * - On 2 or 4: Restores 5 health points to the Rogue and applies a "healing over time"
     *   status effect for 2 rounds. Outputs the result and pauses the game.
     * - On 6: Sets the Rogue's applies a "strength"
     *   status effect for 3 rounds, increasing damage output. Outputs the result and pauses the game.
     *
     * If the Rogue does not have enough power to perform the ability,
     * a message indicating insufficient power is printed.
     */
    @Override
    public void performeUtilityAbility() {
        if (usePower(this.utilityAbilityCost)) {

            int diceRoll = this.random.nextInt(6) + 1;

            switch (diceRoll) {
                case 1, 3, 5 -> {
                    this.print("Bad roll (" + diceRoll + ")");
                    this.pause();
                }
                case 2, 4 -> {
                    this.heal(5);

                    this.applyStatusEffect(StatusEffects.HEALING, 2);
                    this.print("Great roll (" + diceRoll + ")");
                    this.print("Week over time healing for 2 rounds");
                    this.pause();
                }
                case 6 -> {
                    this.setDamageMultiplier(1.25);

                    this.applyStatusEffect(StatusEffects.STRENGTH, 3);
                    this.print("Perfect roll !! (" + diceRoll + ")");
                    this.print("Increased damage for 3 rounds");
                    this.pause();
                }
                default -> { }
            }
        } else {
            this.noPowerSystemOut(this.getPowerString());
        }
    }

    /**
     * Returns the chance of vanishing after a certain number of turns.
     */
    public double getVanishChance() {
        return this.vanishChance;
    }

    /**
     * Resets the vanish variables to their initial values.
     */
    public void resetVanish() {
        this.vanishTurns = 0;
        this.isVanished = false;
        this.vanishChance = 0.6;
    }

    /**
     * Decreases the vanish chance by 0.1 every turn.
     */
    public void decreaseVanishChance() {
        this.vanishChance -= 0.1;
    }

    /**
     * Returns the number of turns the Rogue has been vanished.
     */
    public int getVanishTurns() {
        return this.vanishTurns;
    }

    /**
     * Increments the number of turns the Rogue has been vanished.
     */
    public void incrementVanishTurns() {
        this.vanishTurns++;
    }

    /**
     * Returns true if the Rogue is currently vanished.
     */
    @Override
    public boolean isUntargetable() {
        return this.isVanished;
    }
}
