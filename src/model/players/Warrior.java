package model.players;

import model.Character;
import model.Player;
import model.enums.ClassPower;
import model.enums.PlayerClass;

public class Warrior extends Player {

    private static final int MAX_HP = 100;
    private static final int ATTACK = 7;
    private static final int DEFENCE = 6;
    private static final int POWER = 120;

    public Warrior(String name) {
        super(name, MAX_HP, ATTACK, DEFENCE, POWER);
    }

    @Override
    public void beforeTurn() {

    }

    @Override
    public String getPowerString() {
        return ClassPower.RAGE.toString();
    }

    @Override
    public PlayerClass getClassType() {
        return PlayerClass.WARRIOR;
    }

    @Override
    public void performeUtilityAbitlity() {

    }



    @Override
    public void performeBasicAbility(Character target) {

    }

    @Override
    public void performeSpecialAbility(Character target) {

    }
}
