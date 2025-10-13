package model.players;

import model.Character;

public class Rogue extends Character {
    public Rogue(String name) {
        //TODO : Fix values
        super(name, 0, 0, 0);
    }

    @Override
    public int getTotalAttack() {
        return 0;
    }

    @Override
    public int getTotalDefense() {
        return 0;
    }

    @Override
    public void displayStats() {

    }

    @Override
    public void performSpecialAbility(Character target) {

    }
}
