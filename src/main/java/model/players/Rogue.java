package model.players;

import model.Character;
import model.Player;
import model.enums.ClassPower;
import model.enums.CombatTag;
import model.enums.status.StatusEffects;

import java.util.Random;

public class Rogue extends Player {

    private static final int MAX_HP = 100;
    private static final int ATTACK = 8;
    private static final int DEFENCE = 5;
    private static final int POWER = 150;

    private final String basicAbilityName = "Sinister Strike";
    private final String specialAbilityName = "Vanish";
    private final String utilityAbilityName = "Dice roll";

    private final int basicAbilityCost = 25;
    private final int specialAbilityCost = 60;
    private final int utilityAbilityCost = 10;

    private final String actionVerb = "stab";

    private double vanishChance = 0.6;
    private boolean isVanished = false;

    private Random random = new Random();

    public Rogue(String name) {
        super(name, MAX_HP, ATTACK, DEFENCE, POWER, 1);
    }

    public Rogue(String name, int level) {
        super(name, MAX_HP, ATTACK, DEFENCE, POWER, level);
    }

    @Override
    public String getPowerString() {
        return ClassPower.ENERGY.name();
    }

    @Override
    public CombatTag getCombatTag() {
        return CombatTag.ROGUE;
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
        if (this.isVanished) {
            this.restorePower(5);
            this.checkVanishStatus();
        } else {
            this.restorePower(10);
        }
    }

    @Override
    public void performeBasicAbility(Character target) {
        if (usePower(this.basicAbilityCost)) {
            int rawDamage = (int)(this.getTotalAttack() * 1.5);
            int actualDamage = target.takeDamage(rawDamage, this);

            this.damageAbilitySystemOut(
                    this.basicAbilityName, this.actionVerb, target, actualDamage, rawDamage
            );

        } else {
            this.noPowerSystemOut(this.getPowerString());
        }
    }

    @Override
    public void performeSpecialAbility(Character target) {
        if (!this.isVanished) {
            if (this.usePower(this.specialAbilityCost)) {

                this.applyStatusEffect(StatusEffects.VANISH, -1);
                this.useAbilitySystemOut(this.specialAbilityName);

                this.isVanished = true;

            } else {
                this.noPowerSystemOut(this.getPowerString());
            }
        } else {
            this.print("You are already vanished!");
        }
    }

    @Override
    public void performeUtilityAbility() {
        if (usePower(this.utilityAbilityCost)) {

            int diceRoll = this.random.nextInt(6) + 1;

            switch (diceRoll) {
                case 1, 3, 5 -> { /* No buff */ }
                case 2, 4 -> {
                    this.heal(5);

                    this.applyStatusEffect(StatusEffects.HEALING, 2);
                    this.print("Great roll (" + diceRoll + ")");
                }
                case 6 -> {
                    this.setDamageMultiplier(1.25);

                    this.applyStatusEffect(StatusEffects.STRENGTH, 3);
                    this.print("Perfect roll !! (" + diceRoll + ")");
                }
                default -> { }
            }
        } else {
            this.noPowerSystemOut(this.getPowerString());
        }
    }

    private void checkVanishStatus() {
        if (this.isVanished) {
            if (this.random.nextDouble() >= this.vanishChance) {

                this.print("You remain untargetable and " + this.actionVerb + " again!");

                this.vanishChance -= 0.1; // Increase chance to get out each turn
            } else {
                this.print("You are no longer untargetable.");

                this.removeStatusEffect(StatusEffects.VANISH);
                this.isVanished = false;
                this.vanishChance = 0.6; // Reset for next vanish
            }
        }
    }

    @Override
    public boolean isUntargatable() {
        return this.isVanished;
    }
}
