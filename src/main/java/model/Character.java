package model;

import model.enums.CombatTag;
import model.enums.status.StatusEffects;
import utility.Utility;

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

    protected Character(String name, int maxHp, int attack, int defence) {
        this.name = name;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.attack = attack;
        this.defence = defence;
        this.shield = 0;

        this.statusEffects = new HashMap<>();

        this.level = 1;
        this.experience = 0;
        this.experienceToNextLevel = this.calculateExperienceToNextLevel();
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

    public int getAttack() {
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

    //TODO: UML ZMENA
    public int takeDamage(int damage) {
        if (this.shield > 0) {
            if (damage <= this.shield) {
                this.shield -= damage;

                System.out.println("Shield blocked " + damage + " damage! " + this.shield + " shield left.");
                return 0;
            } else {
                int absorbedDamage = this.shield;
                damage -= absorbedDamage;
                this.shield = 0;

                System.out.println("Shield broken! Absorbed " + absorbedDamage + " damage.");
                return damage;
            }

        }

        int actualDamage = Math.max(1, damage - this.getTotalDefense());
        this.setHp(Math.max(0, this.hp - actualDamage));
        return actualDamage;
    }

    public void heal(int amount) {
        this.hp = Math.min(this.maxHp, this.hp + amount);
    }

    public void applyStatusEffect(StatusEffects effect, int turns) {
        switch (effect) {
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
        this.statusEffects.clear();
    }

    public int getStatusEffectDuration(StatusEffects effect) {
        return this.statusEffects.getOrDefault(effect, 0);
    }

    public Map<StatusEffects, Integer> getStatusEffects() {
        return this.statusEffects;
    }

    protected boolean hasStatusEffect(StatusEffects effect) {
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
                case STRENGTH, INVISIBILITY, VANISH, SHIELD -> { /* nothing needed */ }
                case HEALING -> this.heal(2);
                case BLEEDING -> this.hp -= (this.hp * 2 / 100);
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

    protected int getLevel() {
        return this.level;
    }

    protected void incrementLevel() {
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
        int bars = (int)((double)current / max * 10);
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
