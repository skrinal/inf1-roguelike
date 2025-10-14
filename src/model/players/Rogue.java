package model.players;

import model.Character;
import model.Player;

public class Rogue extends Player {

    public Rogue(String name) {
        super(name, 100, 5, 5, 100);
    }

    @Override
    public String getPowerName() {
        return "";
    }

    @Override
    public void displayStats() {

    }

    @Override
    public void performSpecialAbility(Character target) {

    }
}
