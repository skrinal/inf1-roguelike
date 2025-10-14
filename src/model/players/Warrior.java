package model.players;

import model.Character;
import model.Player;

public class Warrior extends Player {

    public Warrior(String name) {
        super(name, 100, 5, 5, 0);
    }

    @Override
    public String getPowerName() {
        return "RAGE";
    }

    @Override
    public void displayStats() {

    }

    @Override
    public void performSpecialAbility(Character target) {

    }
}
