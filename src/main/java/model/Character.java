package model;

import model.enums.CombatTag;
import model.enums.status.StatusEffects;
import output.ConsoleOutput;
import output.SystemOutput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents an abstract character in the game with properties such as health, attack, defense,
 * level, and status effects. The class provides functionality to manage and scale stats,
 * apply and remove status effects, handle damage and healing, and interact with other characters.
 *
 * This serves as a base class for specific types of characters like enemies or player-controlled
 * characters. Subclasses can extend and override behavior as required.
 */
public abstract class Character {
    private final String name;
    private int hp;
    private int maxHp;
    private int attack;
    private int defence;
    private int shield;

    private HashMap<StatusEffects, Integer> statusEffects;

    private double damageMultiplier = 1.0;
    private double defenceMultiplier = 1.0;

    private int level;

    private SystemOutput out;

    /**
     * Constructs a new Character with specified attributes.
     *
     * @param name     The name of the character.
     * @param maxHp    The maximum health points (HP) of the character.
     * @param attack   The attack value of the character.
     * @param defence  The defence value of the character.
     * @param level    The starting level of the character. If greater than 1, the character's stats are scaled accordingly.
     */
    protected Character(String name, int maxHp, int attack, int defence, int level) {
        this.name = name;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.attack = attack;
        this.defence = defence;

        this.shield = 0;
        this.statusEffects = new HashMap<>();

        this.level = 1;
        if (level > 1) {
            this.initializeAtLevel(level);
        }

        this.out = new ConsoleOutput();
    }

    /**
     * Sets the system output for the character, allowing it to use the specified
     * SystemOutput instance for printing messages or performing pauses.
     *
     * @param out the SystemOutput instance to be used for system output operations
     */
    public void setSystemOutput(SystemOutput out) {
        this.out = out;
    }

    /**
     * Prints the specified text using the system output defined for the character.
     *
     * @param text The text to be printed.
     */
    public void print(String text) {
        this.out.println(text);
    }

    /**
     * Pauses the execution of the current thread for the specified number of milliseconds.
     */
    public void pause() {
        this.out.pause();
    }

    private void initializeAtLevel(int targetedLevel) {
        for (int i = 1; i < targetedLevel; i++) {
            this.scaleStats();
        }
    }

    private void scaleStats() {
        if (this instanceof Enemy enemy) {
            int currentLevel = this.getLevel();

            int newMaxHp = this.getMaxHp() + 15 + (currentLevel * 2);
            int newAttack = this.getAttack() + 2 + currentLevel;
            int newDefence = this.getDefence() + 2 + (int)Math.round((double)currentLevel / 2);
            int newGold = enemy.getGoldReward() + (currentLevel * 10);
            int newXpReward = enemy.getXpReward() + (currentLevel * 10);

            this.setMaxHp(newMaxHp);
            this.setHp(newMaxHp);
            this.setAttack(newAttack);
            this.setDefence(newDefence);
            enemy.setGoldReward(newGold);
            enemy.setXpReward(newXpReward);
        } else {
            this.maxHp += 10 + (this.level * 2);
            this.hp = this.maxHp;
            this.attack += 2 + this.level;
            this.defence += 1 + (int)((double)this.level / 2);
        }

        this.incrementLevel();
    }

    /**
     * Returns the name of the character.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the current health points (HP) of the character.
     */
    public int getHp() {
        return this.hp + this.shield;
    }

    protected void setHp(int hp) {
        this.hp = hp;
    }

    /**
     * Returns the maximum health points (HP) of the character.
     */
    public int getMaxHp() {
        return this.maxHp;
    }

    protected void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    protected int getAttack() {
        return this.attack;
    }

    protected void setAttack(int attack) {
        this.attack = attack;
    }

    protected int getDefence() {
        return this.defence;
    }

    protected void setDefence(int defence) {
        this.defence = defence;
    }

    /**
     * Checks if the character is alive.
     */
    public boolean isAlive() {
        return this.hp > 0;
    }

    /**
     * Sets the shield of the character to the specified value.
     */
    public void setShield(int shield) {
        this.shield = Math.max(0, shield);
    }

    /**
     * Handles damage taken by the character, factoring in shields, defenses, and status effects.
     *
     * If the character has a shield, the shield will absorb damage until it is depleted. Any
     * remaining damage after the shield is depleted is mitigated by the character's defense.
     * Certain status effects, like THORNS or DEFENSIVE, may reflect a portion of the damage
     * back to the attacker.
     *
     * @param damage The amount of incoming damage before mitigation.
     * @param attacker The character that is dealing the damage.
     * @return The actual damage dealt to the character's health after all mitigations and effects.
     */
    public int takeDamage(int damage, Character attacker) {
        int multiplayerDamage = (int)(attacker.getDamageMultiplier() * damage);

        if (this.shield > 0) {
            if (multiplayerDamage <= this.shield) {
                this.shield -= multiplayerDamage;

                this.print("Shield blocked " + multiplayerDamage + " damage! " + this.shield + " shield left.");
                return 0;
            } else {
                int absorbedDamage = this.shield;
                multiplayerDamage -= absorbedDamage;
                this.shield = 0;

                this.print("Shield broken! Absorbed " + absorbedDamage + " damage.");
                return multiplayerDamage;
            }
        }

        int actualDamage = Math.max(1, multiplayerDamage - this.getTotalDefense());
        this.setHp(Math.max(0, this.hp - actualDamage));

        if (this.hasStatusEffect(StatusEffects.THORNS)) {
            int reflected = Math.max(1, (int)(actualDamage * StatusEffects.THORNS.getReflectionPercent()) );
            attacker.takeTrueDamage(reflected);

            this.print(attacker.getName() + " is pierced by thorns for " + reflected + " damage!");
        }

        if (this.hasStatusEffect(StatusEffects.DEFENSIVE)) {
            int reflected = Math.max(1, (int)(actualDamage * StatusEffects.THORNS.getReflectionPercent()));
            attacker.takeTrueDamage(reflected);

            this.print(attacker.getName() + " is pierced by thorns for " + reflected + " damage!");
        }

        return actualDamage;
    }

    /**
     * Applies true damage to the character, directly reducing its health points (HP)
     * without any mitigation from shields, defenses, or other effects.
     *
     * @param damage The amount of true damage to be dealt to the character.
     * @return The amount of damage applied to the character.
     */
    public int takeTrueDamage(int damage) {
        this.setHp(Math.max(0, this.hp - damage));
        return damage;
    }

    /**
     * Heals the character by the specified amount, up to its maximum health points (HP).
     */
    public void heal(int amount) {
        this.hp = Math.min(this.maxHp, this.hp + amount);
    }

    /**
     * Applies the specified status effect to the character for the specified number of turns.
     */
    public void applyStatusEffect(StatusEffects effect, int turns) {
        if (this.statusEffects.containsKey(effect)) {
            this.statusEffects.put(effect, turns);

        } else {
            effect.onApply(this);
            this.statusEffects.put(effect, turns);
        }
    }

    /**
     * Removes the specified status effect from the character.
     */
    public void removeStatusEffect(StatusEffects effect) {
        if (this.statusEffects.containsKey(effect)) {
            effect.onRemove(this);

            this.statusEffects.remove(effect);
        }
    }

    /**
     * Removes all status effects from the character that are not stances.
     */
    public void removeAllStatusEffects() {
        List<StatusEffects> toRemove = new ArrayList<>();

        for (StatusEffects effect : this.statusEffects.keySet()) {
            if (!this.checkIfStance(effect)) {
                toRemove.add(effect);
                effect.onRemove(this);
            }
        }

        for (StatusEffects effect : toRemove) {
            this.removeStatusEffect(effect);
        }
    }

    private boolean checkIfStance(StatusEffects stance) {
        switch (stance) {
            case AGGRESSIVE, DEFENSIVE, BALANCED -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    /**
     * Returns a map of all status effects currently applied to the character.
     */
    public Map<StatusEffects, Integer> getStatusEffects() {
        return this.statusEffects;
    }

    /**'
     * Checks if the character has the specified status effect applied.
     */
    public boolean hasStatusEffect(StatusEffects effect) {
        return this.statusEffects.containsKey(effect);
    }

    /**
     * Checks if the character has no status effects applied.
     */
    public boolean isStatusEffectsEmpty() {
        return this.statusEffects.isEmpty();
    }

    /**
     * Updates the status effects currently applied to the character.
     *
     * This method iterates through all active status effects and performs their effects during this turn
     * by running their respective onTik method. Status effects with a limited duration have their
     * remaining turns decreased by 1. When a status effect's duration reaches 0, it is removed from the character.
     * Status effects with a duration of -1 persist indefinitely and are not decremented.
     *
     * If there are no active status effects when this method is runned, the method exits immediately.
     */
    public void updateStatusEffects() {
        if (this.isStatusEffectsEmpty()) {
            return;
        }

        List<StatusEffects> toRemove = new ArrayList<>();

        for (var entry : this.statusEffects.entrySet()) {

            StatusEffects statusEffect = entry.getKey();
            int turnsLeft = entry.getValue();

            statusEffect.onTick(this);

            if (turnsLeft != -1) {
                turnsLeft--;
                if (turnsLeft == 0) {
                    toRemove.add(statusEffect);
                } else {
                    this.statusEffects.put(statusEffect, turnsLeft);
                }
            }
        }

        for (StatusEffects expired : toRemove) {
            this.removeStatusEffect(expired);
        }
    }

    /**
     * Sets the damage multiplier for the character.
     */
    public void setDamageMultiplier(double damageMultiplier) {
        this.damageMultiplier = damageMultiplier;
    }

    /**
     * Returns the damage multiplier for the character.
     */
    public double getDamageMultiplier() {
        return this.damageMultiplier;
    }

    /**
     * Sets the defence multiplier for the character.
     */
    public void setDefenceMultiplier(double defenceMultiplier) {
        this.defenceMultiplier = defenceMultiplier;
    }

    /**
     * Returns the defence multiplier for the character.
     */
    public double getDefenceMultiplier() {
        return this.defenceMultiplier;
    }

    /**
     * Returns the current level of the character.
     */
    public int getLevel() {
        return this.level;
    }

    protected void incrementLevel() {
        this.level++;
    }

    /**
     * Returns a string representation of the character's health bar.
     */
    public String getHealthBar() {
        return this.getBar(this.hp, this.getMaxHp());
    }

    protected String getBar(int current, int max) {
        int bars = (current * 10) / max;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            result.append(i < bars ? "█" : "░");
        }
        return result.toString();
    }

    /**
     * Calculates and returns the total attack value of this character.
     * The total attack takes into account the character's base attack
     * value, any applied status effects, relevant multipliers, and item bonuses.
     *
     * @return the total attack value of the character as an integer
     */
    public abstract int getTotalAttack();

    /**
     * Calculates and returns the total defense value of this character.
     * The total defense takes into account the character's base defense
     * value, any applied status effects, relevant multipliers, and item bonuses.
     *
     * @return the total defense value of the character as an integer
     */
    public abstract int getTotalDefense();

    /**
     * Executes the basic ability of the character. This action typically involves interaction
     * with another character, such as dealing damage, applying a status effect, or any other
     * predefined basic ability behavior specific to the implementing character.
     *
     * @param target The target character on which the basic ability will be performed.
     */
    public abstract void performBasicAbility(Character target);

    /**
     * Executes the special ability of the character. This action typically involves
     * a unique or powerful action that the character can perform, often with an
     * impactful effect on the target.
     *
     * @param target The target character on which the special ability will be performed.
     */
    public abstract void performSpecialAbility(Character target);

    /**
     * Returns the combat tag of the character.
     */
    public abstract CombatTag getCombatTag();
}
