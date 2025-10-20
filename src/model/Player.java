package model;

import java.util.HashMap;

public abstract class Player extends Character {
    private int power;
    private int maxPower;
    private int gold;
    private HashMap<Item, Integer> inventory;
    private Item equippedWeapon;
    private Item equippedArmor;

    public Player(String name, int maxHp, int attack, int defence, int maxPower) {
        super(name, maxHp, attack, defence);
        this.maxPower = maxPower;
        this.power = maxPower;
        this.gold = 0;
        this.inventory = new HashMap<>();
        this.equippedArmor = null;
        this.equippedWeapon = null;
    }

    public int getPower() {
        return this.power;
    }

    public int getMaxPower() {
        return this.maxPower;
    }

    public int getGold() {
        return this.gold;
    }

    public HashMap<Item, Integer> getInventory() {
        return this.inventory;
    }

    public void restorePower(int amount) {
        this.power = Math.min(this.maxPower, this.power + amount);
    }

    public boolean usePower(int amount) {
        if (this.power >= amount) {
            this.power -= amount;
        } else {
            return false;
        }
        return true;
    }

    public void addGold(int amount) {
        this.gold += amount;
    }

    //TODO: Nastudovat
    public void addItem(Item item) {
        this.inventory.put(item, this.inventory.getOrDefault(item, 0) + 1);
    }

    public abstract String getPowerName();


    @Override
    public int getTotalAttack() {
        return getAttack() + (this.equippedWeapon != null ? this.equippedWeapon.getValue() : 0);
    }

    @Override
    public int getTotalDefense() {
        return getDefence() + (this.equippedArmor != null ? this.equippedArmor.getValue() : 0);
    }

    public Item getEquippedWeapon(){
        return this.equippedWeapon;
    }
    public Item getEquippedArmor(){
        return this.equippedArmor;
    }

    public void setEquippedArmor(Item equippedArmor) {
        this.equippedArmor = equippedArmor;
    }

    public void setEquippedWeapon(Item equippedWeapon) {
        this.equippedWeapon = equippedWeapon;
    }
}
