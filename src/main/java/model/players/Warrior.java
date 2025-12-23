package model.players;

import model.Character;
import model.Player;
import model.enums.ClassPower;
import model.enums.CombatTag;
import model.enums.status.StatusEffects;
import utility.Utility;

public class Warrior extends Player {

    private static final int MAX_HP = 100;
    private static final int ATTACK = 7;
    private static final int DEFENCE = 6;
    private static final int POWER = 120;

    private final String basicAbilityName = "Bloodthirst";
    private final String specialAbilityName = "Execute";
    private final String utilityAbilityName = "War Stance";

    private final String actionVerb = "slash";

    private final int basicAbilityCost = 20;
    private final int specialAbilityCost = 30;
    private final int utilityAbilityCost = 10;

    private StatusEffects stance = StatusEffects.BALANCED;

    public Warrior(String name) {
        super(name, MAX_HP, ATTACK, DEFENCE, POWER);
    }

    @Override
    public String getPowerString() {
        return ClassPower.RAGE.toString();
    }

    @Override
    public CombatTag getCombatTag() {
        return CombatTag.WARRIOR;
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
        switch (this.stance) {
            case DEFENSIVE -> this.restorePower(15);
            case AGGRESSIVE -> this.restorePower(10);
            case BALANCED -> this.restorePower(20);
        }
    }

    @Override
    public void performeBasicAbility(Character target) {
        if (usePower(this.basicAbilityCost)) {
            int damage = (int)(this.getTotalAttack() * 1.4);
            int rawDamage = target.takeDamage(damage, this);

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
            if (this.isBelow15Percent(target.getHp(), target.getMaxHp())) {
                target.takeTrueDamage(Integer.MAX_VALUE);

                System.out.println(target.getName() + " has been executed");

            } else {
                int damage = (int)(this.getTotalAttack() * 0.4);
                int rawDamage = target.takeDamage(damage, this);

                System.out.println(target.getName() + " is not under 15% HP");
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

    @Override
    public void performeUtilityAbility() {
        if (usePower(this.utilityAbilityCost)) {
            System.out.println("Current stance: " + this.stance);
            System.out.println("Choose a stance:");
            System.out.println("1. Aggressive");
            System.out.println("2. Defensive");
            System.out.println("3. Balanced");

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

        System.out.println("Stance changed to " + this.stance);
    }
}
