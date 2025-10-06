package model;

import java.util.HashMap;

public abstract class Player extends Character {
    protected int power;
    protected int maxPower;
    protected int gold;
    protected HashMap<Item, Integer> inventory;
    protected Item equipedWeapond;
    protected Item equipedArmor;


    public Player(String name, int maxHp, int attack, int defence, int maxPower) {
        super(name, maxHp, attack, defence);
        this.maxPower = maxPower;
        this.power = maxPower;
        this.gold = 0;
        this.inventory = new HashMap<>();
    }

    public int getPower() {return power;}

    public int getMaxPower() {return maxPower;}

    public int getGold() {return gold;}

    public void restorePower(int amount) {
        power = Math.min(maxPower, power + amount);
    }

    public boolean usePower(int amount) {
        if (power >= amount) {
            power -= amount;
        } else {
            return false;
        }
        return true;
    }

    public void addGold(int amount) {gold += amount;}

    public void addItem(Item item) {inventory.put(item, inventory.getOrDefault(item, 0) + 1);}

    public abstract String getPowerName();

    @Override
    public int getTotalAttack() {
        return attack + (equipedWeapond != null ? equipedWeapond.getValue() : 0);
    }
}
