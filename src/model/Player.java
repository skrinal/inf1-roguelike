package model;

import model.enums.GameState;
import model.enums.PlayerClass;
import model.enums.room.RoomType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public abstract class Player extends Character {
    private ArrayList<RoomType> completedRooms;
    private int power;
    private final int maxPower;
    private int gold;
    private final HashMap<Item, Integer> inventory;
    private Item equippedWeapon;
    private Item equippedArmor;

    public Player(String name, int maxHp, int attack, int defence, int maxPower) {
        super(name, maxHp, attack, defence);
        this.completedRooms = new ArrayList<>();
        this.maxPower = maxPower;
        this.power = maxPower;
        this.gold = 0;
        this.inventory = new HashMap<>();
        this.equippedArmor = null;
        this.equippedWeapon = null;
    }

    public ArrayList<RoomType> getCompletedRooms() {
        return this.completedRooms;
    }

    public void addCompletedRoom(RoomType roomType) {
        this.completedRooms.add(roomType);
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

    public void addGold(int amount) {
        this.gold += amount;
    }

    public HashMap<Item, Integer> getInventory() {
        return this.inventory;
    }

    //TODO: Nastudovat
    public void addItem(Item item) {
        this.inventory.put(item, this.inventory.getOrDefault(item, 0) + 1);
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

    public String getPowerBar() {
        return this.getBar(this.getPower(), this.getMaxPower());
    }
    

    @Override
    public int getTotalAttack() {
        return this.getAttack() + (this.equippedWeapon != null ? this.equippedWeapon.getValue() : 0);
    }

    @Override
    public int getTotalDefense() {
        return this.getDefence() + (this.equippedArmor != null ? this.equippedArmor.getValue() : 0);
    }

    public GameState handleStats(Scanner input) {
        System.out.println("\n=== STATS ===");
        this.displayStats();
        System.out.print("\nPress Enter to return...");
        input.nextLine();
        return GameState.GAME;
    }

    public Item getEquippedWeapon() {
        return this.equippedWeapon;
    }

    public void setEquippedWeapon(Item equippedWeapon) {
        this.equippedWeapon = equippedWeapon;
    }

    public Item getEquippedArmor() {
        return this.equippedArmor;
    }

    public void setEquippedArmor(Item equippedArmor) {
        this.equippedArmor = equippedArmor;
    }

    public void displayStats() {
        System.out.println("╔═════════════════════════════════╗");
        System.out.println("║  " + this.getName() + " [" + this.getClassType() + "]");
        System.out.println("╠═════════════════════════════════╣");
        System.out.println("║ HP:      [" + this.getHealthBar() + "] " + this.getHp() + "/" + this.getMaxHp());
        System.out.println("║ " + this.getPowerString() + ":    [" + this.getPowerBar() + "] " + this.getPower() + "/" + this.getMaxPower());
        System.out.println("║ Attack:  " + this.getTotalAttack());
        System.out.println("║ Defense: " + this.getTotalDefense());
        System.out.println("║ Gold:    " + this.getGold());
        System.out.println("║ ");
        System.out.println("║ Level:   " + this.getLevel() + " - " + this.getExperience() + "/" + this.getExperienceToNextLevel());
        System.out.println("║ ");
        System.out.println("║ Armor:   " + (this.getEquippedArmor() == null ? "None" : this.getEquippedArmor().getName() + " + " + this.getEquippedArmor().getValue() + " Armor"));
        System.out.println("║ Weapon:  " + (this.getEquippedWeapon() == null ? "None" : this.getEquippedWeapon().getName() + " + " + this.getEquippedWeapon().getValue() + " Damage" ));
        System.out.println("╚═════════════════════════════════╝");
    }

    public abstract String getPowerString();
    public abstract PlayerClass getClassType();
    public abstract void performeUtilityAbitlity();

}
