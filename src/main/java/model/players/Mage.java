package model.players;

import model.Character;
import model.Player;
import model.enums.ClassPower;
import model.enums.CombatTag;
import model.enums.status.StatusEffects;
import utility.Utility;

public class Mage extends Player {
    private static final int MAX_HP = 80;
    private static final int ATTACK = 10;
    private static final int DEFENCE = 3;
    private static final int POWER = 100;

    private final String basicAbilityName = "Frostbolt";
    private final String specialAbilityName = "Fireblast";
    private final String utilityAbilityName = "Cloak of Shadows";

    private final int basicAbilityCost = 10;
    private final int specialAbilityCost = 50;
    private final int utilityAbilityCost = 15;

    private final int invisibilityDuration = -1;
    private final int shieldDuration = 1;

    private final String actionVerb = "blast";

    private double invisibilityChance = 0.4;
    private boolean isInvisible = false;

    public Mage(String name) {
        super(name, MAX_HP, ATTACK, DEFENCE, POWER, 1);
    }

    public Mage(String name, int level) {
        super(name, MAX_HP, ATTACK, DEFENCE, POWER, level);
    }

    @Override
    public String getPowerString() {
        return ClassPower.MANA.name();
    }

    @Override
    public CombatTag getCombatTag() {
        return CombatTag.MAGE;
    }

    @Override
    public String getBasicAbilityName() {
        return this.basicAbilityName;
    }

    @Override
    public String getSpecialAbilityName() {
        return this.specialAbilityName;
    }

    @Override
    public String getUtilityAbilityName() {
        return this.utilityAbilityName;
    }

    @Override
    public int getBasicAbilityCost() {
        return this.basicAbilityCost;
    }

    @Override
    public int getSpecialAbilityCost() {
        return this.specialAbilityCost;
    }

    @Override
    public int getUtilityAbilityCost() {
        return this.utilityAbilityCost;
    }

    @Override
    public void beforeTurn() {
        if (this.isInvisible) {
            this.restorePower(5);
            this.checkInvisibilityStatus();
        } else {
            this.restorePower(15);
        }
    }

    @Override
    public void performeBasicAbility(Character target) {
        if (usePower(this.basicAbilityCost)) {
            int rawDamage = (int)(this.getTotalAttack() * 1.3);
            int damage = target.takeDamage(rawDamage, this);

            this.damageAbilitySystemOut(
                    this.basicAbilityName, this.actionVerb, target, damage, rawDamage
            );

        } else {
            this.noPowerSystemOut(this.getPowerString());
        }
    }

    @Override
    public void performeSpecialAbility(Character target) {
        if (usePower(this.specialAbilityCost)) {
            int rawDamage = (int)(this.getTotalAttack() * 2.5);
            int damage = target.takeDamage(rawDamage, this);

            this.damageAbilitySystemOut(
                    this.specialAbilityName, this.actionVerb, target, damage, rawDamage
            );

        } else {
            this.noPowerSystemOut(this.getPowerString());
        }
    }

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

    private void checkInvisibilityStatus() {
        if (this.isInvisible) {
            if (Utility.getRandomDouble() >= this.invisibilityChance) {

                this.print("You remain invisible!");
                this.invisibilityChance -= 0.1;
            } else {
                this.print("You are no longer invisible!");

                this.removeStatusEffect(StatusEffects.INVISIBILITY);
                this.isInvisible = false;
                this.invisibilityChance = 0.5;

                this.applyStatusEffect(StatusEffects.SHIELD, this.shieldDuration);
                this.print("Small shield applied");
            }
        }
    }


}
