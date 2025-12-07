package model.players;

import model.Character;
import model.Player;
import model.enums.ClassPower;
import model.enums.PlayerClass;

public class Mage extends Player {

    private static final int MAX_HP = 80;
    private static final int ATTACK = 10;
    private static final int DEFENCE = 3;
    private static final int POWER = 100;

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
            int rawDamage = (int)(this.getTotalAttack() * 1.2);
            int actualDamage = target.takeDamage(rawDamage);

            System.out.println("Fireball! You blast " + target.getName() + " for "
                    + actualDamage + " damage! (" + rawDamage + " raw)");
        } else {
            System.out.println("Not enough Mana!");
        }
    }

    @Override
    public void performeUtilityAbitlity() {
        // private arralist<Buff>
        // spravit mozno buff class
        // preddefinovat buffy cez enum ?
    }




}
