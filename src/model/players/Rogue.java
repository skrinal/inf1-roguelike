package model.players;

import model.Character;
import model.Player;
import model.enums.ClassPower;
import model.enums.PlayerClass;

public class Rogue extends Player {

    private static final int MAX_HP = 100;
    private static final int ATTACK = 8;
    private static final int DEFENCE = 5;
    private static final int POWER = 150;

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
    public void performSpecialAbility(Character target) {

    }
}
