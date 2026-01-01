package model;

import model.enums.CombatTag;
import model.enums.status.StatusEffects;
import output.ConsoleOutput;
import output.SystemOutput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    private int experience;
    private int experienceToNextLevel;

    private SystemOutput out;

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

        this.experience = 0;
        this.experienceToNextLevel = this.calculateExperienceToNextLevel();

        this.out = new ConsoleOutput();
    }

    public void setSystemOutput(SystemOutput out) {
        this.out = out;
    }

    protected void print(String text) {
        this.out.println(text);
    }

    protected void pause() {
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

            this.setMaxHp(newMaxHp);
            this.setHp(newMaxHp);
            this.setAttack(newAttack);
            this.setDefence(newDefence);
            enemy.setGoldReward(newGold);
        } else {
            this.maxHp += 10 + (this.level * 2);
            this.hp = this.maxHp;
            this.attack += 2 + this.level;
            this.defence += 1 + (int)((double)this.level / 2);
        }

        this.incrementLevel();
    }

    public String getName() {
        return this.name;
    }

    public int getHp() {
        return this.hp + this.shield;
    }

    protected void setHp(int hp) {
        this.hp = hp;
    }

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

    public boolean isAlive() {
        return this.hp > 0;
    }

    private void setShield(int shield) {
        this.shield = Math.max(0, shield);
    }

    //TODO: EnemyType treba pouzit kde su damagePercenate -> asi fix uz
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
            int reflected = Math.max(1, actualDamage / 4);
            attacker.takeTrueDamage(reflected);

            this.print(attacker.getName() + " is pierced by thorns for " + reflected + " damage!");
        }

        return actualDamage;
    }

    public int takeTrueDamage(int damage) {
        this.setHp(Math.max(0, this.hp - damage));
        return damage;
    }

    public void heal(int amount) {
        this.hp = Math.min(this.maxHp, this.hp + amount);
    }

    public void applyStatusEffect(StatusEffects effect, int turns) {
        switch (effect) {
            //Warrior
            case AGGRESSIVE -> this.setDamageMultiplier(1.35);
            case DEFENSIVE -> this.setDefenceMultiplier(1.35);
            case BALANCED -> {
                this.setDamageMultiplier(1.15);
                this.setDefenceMultiplier(1.15);
            }
        }

        if (effect == StatusEffects.SHIELD) {
            int shieldAmount = (this.hp * 15) / 100;
            this.setShield(shieldAmount);
        }
        this.statusEffects.put(effect, turns);
    }

    protected void removeStatusEffect(StatusEffects effect) {
        switch (effect) {
            case AGGRESSIVE -> this.setDamageMultiplier(1.0);
            case DEFENSIVE -> this.setDefenceMultiplier(1.0);
            case BALANCED -> {
                this.setDamageMultiplier(1.0);
                this.setDefenceMultiplier(1.0);
            }
        }
        this.statusEffects.remove(effect);
    }

    public void removeAllStatusEffects() {
        for (StatusEffects effect : this.statusEffects.keySet()) {
            if (!this.checkIfStance(effect)) {
                this.removeStatusEffect(effect);
            }
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


    public Map<StatusEffects, Integer> getStatusEffects() {
        return this.statusEffects;
    }

    public boolean hasStatusEffect(StatusEffects effect) {
        return this.statusEffects.containsKey(effect);
    }

    public boolean isStatusEffectsEmpty() {
        return this.statusEffects.isEmpty();
    }

    public void updateStatusEffects() {
        if (this.isStatusEffectsEmpty()) {
            return;
        }

        ArrayList<StatusEffects> toRemove = new ArrayList<>();

        for (var effect : this.statusEffects.entrySet()) {
            StatusEffects statusEffect = effect.getKey();
            int turnsLeft = effect.getValue() - 1;

            switch (statusEffect) {
                case HEALING -> this.heal(this.maxHp * 2 / 100);
                case BLEEDING -> this.hp -= (this.maxHp * 2 / 100);

                //Skeleton
                case SKELETON_CURSE -> this.hp -= (this.maxHp / 100);

                default -> { /* nothing needed */ }
            }
            this.statusEffects.put(statusEffect, turnsLeft);
            if (turnsLeft == 0) {
                toRemove.add(statusEffect);
            }
        }

        toRemove.forEach(this.statusEffects::remove);
    }

    protected void setDamageMultiplier(double damageMultiplier) {
        this.damageMultiplier = damageMultiplier;
    }
    protected double getDamageMultiplier() {
        return this.damageMultiplier;
    }

    protected void setDefenceMultiplier(double defenceMultiplier) {
        this.defenceMultiplier = defenceMultiplier;
    }
    protected double getDefenceMultiplier() {
        return this.defenceMultiplier;
    }

    public int getLevel() {
        return this.level;
    }

    private void incrementLevel() {
        this.level++;
    }

    protected int getExperience() {
        return this.experience;
    }

    // TODO: Come up with system to lose experience
    // public void loseExperience(int amount) {}

    protected int getExperienceToNextLevel() {
        return this.experienceToNextLevel;
    }

    public void gainExperience(int amount) {
        this.experience += amount;
        while (this.experience >= this.experienceToNextLevel) {
            this.levelUp();
        }
    }

    private void levelUp() {
        this.experience -= this.experienceToNextLevel;
        this.level++;
        this.experienceToNextLevel = this.calculateExperienceToNextLevel();

        this.maxHp += 10 + (this.level * 2);
        this.hp = this.maxHp;
        this.attack += 2 + this.level;
        this.defence += 1 + (int)((double)this.level / 2);
    }

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

    private int calculateExperienceToNextLevel() {
        return (int)(100 * Math.pow(1.2, this.level));
    }

    public abstract int getTotalAttack();
    public abstract int getTotalDefense();

    public abstract void performeBasicAbility(Character target);
    public abstract void performeSpecialAbility(Character target);

    public abstract CombatTag getCombatTag();
}
