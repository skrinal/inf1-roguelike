package model.players;

import model.Character;
import model.Player;
import model.enums.ClassPower;
import model.enums.PlayerClass;
import model.enums.status.StatusEffects;

import java.util.Random;

public class Rogue extends Player {

    private static final int MAX_HP = 100;
    private static final int ATTACK = 8;
    private static final int DEFENCE = 5;
    private static final int POWER = 150;

    private double vanishChance = 0.6;
    private boolean isVanished = false;

    private Random random = new Random();

    public Rogue(String name) {
        super(name, MAX_HP, ATTACK, DEFENCE, POWER);
    }

    @Override
    public String getPowerString() {
        return ClassPower.ENERGY.name();
    }

    @Override
    public PlayerClass getClassType() {
        return PlayerClass.ROGUE;
    }

    @Override
    public void beforeTurn() {
        if (!this.isVanished) {
            this.restorePower(5);
            this.checkVanishStatus();
        }
        this.restorePower(15);
    }

    @Override
    public boolean isUntargatable() {
        return this.isVanished;
    }

    @Override
    public void performeUtilityAbitlity() {
        if (usePower(10)) {

            int diceRoll = this.random.nextInt(6) + 1;

            switch (diceRoll) {
                case 1, 3, 5 -> { /* No buff */ }
                case 2, 4 -> {
                    this.heal(5);

                    this.applyStatusEffect(StatusEffects.HEALING, 2);
                    System.out.println("Great roll (" + diceRoll + ")");
                }
                case 6 -> {
                    this.setDamageMultiplier(1.25);

                    this.applyStatusEffect(StatusEffects.STRENGTH, 3);
                    System.out.println("Perfect roll !! (" + diceRoll + ")");
                }
                default -> { }
            }
        }
    }

    @Override
    public void performeBasicAbility(Character target) {
        if (usePower(25)) {
            int rawDamage = (int)(this.getTotalAttack() * 1.5);
            int actualDamage = target.takeDamage(rawDamage);

            System.out.println("Sinister Strike! You stab " + target.getName() + " for "
                    + actualDamage + " damage! (" + rawDamage + " raw)");
        } else {
            System.out.println("Not enough Energy!");
        }
    }

    @Override
    public void performeSpecialAbility(Character target) {
        if (!this.isVanished) {
            if (this.usePower(60)) {
                this.applyStatusEffect(StatusEffects.VANISH, -1);
                System.out.println("You have used Vanish!");
                this.isVanished = true;
                this.vanishChance = 0.6;

            } else {
                System.out.println("Not enough Energy!");
            }
        } else {
            System.out.println("You are already vanished!");
        }
    }

    private void checkVanishStatus() {
        if (this.isVanished) {
            if (this.random.nextDouble() >= this.vanishChance) {

                System.out.println("You remain untargetable and strike again!");

                this.vanishChance -= 0.1; // Increase chance to get out each turn
            } else {
                System.out.println("You are no longer untargetable.");

                this.removeStatusEffect(StatusEffects.VANISH);
                this.isVanished = false;
                this.vanishChance = 0.6; // Reset for next vanish
            }
        }
    }

}
