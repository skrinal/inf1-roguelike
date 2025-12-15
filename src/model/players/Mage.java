package model.players;

import model.Character;
import model.Enemy;
import model.Player;
import model.enums.ClassPower;
import model.enums.PlayerClass;
import model.enums.status.StatusEffects;

import java.util.Random;

public class Mage extends Player {
    private static final int MAX_HP = 80;
    private static final int ATTACK = 10;
    private static final int DEFENCE = 3;
    private static final int POWER = 100;

    private double invisibilityChance = 0.4;
    private boolean isInvisible = false;

    private final Random random = new Random();

    public Mage(String name) {
        super(name, MAX_HP, ATTACK, DEFENCE, POWER);
    }

    @Override
    public String getPowerString() {
        return ClassPower.MANA.name();
    }

    @Override
    public PlayerClass getClassType() {
        return PlayerClass.MAGE;
    }

    // TODO: Damage logic is bad
    @Override
    public void performeSpecialAbility(Character target) {
        if (usePower(50)) {
            int rawDamage = (int)(this.getTotalAttack() * 2.5);
            int actualDamage = target.takeDamage(rawDamage);

            System.out.println("FIREBLAST! You blast " + target.getName() + " for "
                    + actualDamage + " damage! (" + rawDamage + " raw)");
        } else {
            System.out.println("Not enough Mana!");
        }
    }
    @Override
    public void performeBasicAbility(Character target) {
        if (usePower(10)) {
            int rawDamage = (int)(this.getTotalAttack() * 1.3);
            int actualDamage = target.takeDamage(rawDamage);

            System.out.println("Fireball! You blast " + target.getName() + " for "
                    + actualDamage + " damage! (" + rawDamage + " raw)");
        } else {
            System.out.println("Not enough Mana!");
        }
    }

    @Override
    public void performeUtilityAbitlity() {
        if (!this.isInvisible) {
            if (this.usePower(15)) {
                this.applyStatusEffect(StatusEffects.INVISIBILITY, -1);
                System.out.println("You have used !");
            } else {
                System.out.println("Not enough Mana!");
            }
        } else {
            System.out.println("You are already invisible!");
        }
    }

    @Override
    public void beforeTurn() {
        if (!this.isInvisible) {
            this.restorePower(5);
            this.checkInvisibilityStatus();

        }
        restorePower(10);
    }

    private void checkInvisibilityStatus() {
        if (this.isInvisible) {
            if (this.random.nextDouble() >= this.invisibilityChance) {

                System.out.println("You remain invisible!");
                this.invisibilityChance -= 0.1;
            } else {
                System.out.println("You are no longer invisible!");

                this.removeStatusEffect(StatusEffects.INVISIBILITY);
                this.isInvisible = false;
                this.invisibilityChance = 0.5;
            }
        }
    }


}
