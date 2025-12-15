package model;

import model.enums.GameState;
import model.enums.InventoryView;
import model.enums.ItemType;
import model.enums.PlayerClass;
import model.enums.room.RoomType;
import model.enums.status.StatusEffects;
import utility.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class Player extends Character {
    private final int maxPower;
    private final HashMap<Item, Integer> inventory;
    private HashMap<StatusEffects, Integer> statusEffects;
    private List<RoomType> completedRooms;
    private List<RoomType> treasureFound;
    private int power;
    private int gold;
    private Item equippedWeapon;
    private Item equippedArmor;
    private double damageMultiplier = 1.0;
    private double defenceMultiplier = 1.0;

    protected Player(String name, int maxHp, int attack, int defence, int maxPower) {
        super(name, maxHp, attack, defence);
        this.statusEffects = new HashMap<>();
        this.completedRooms = new ArrayList<>();
        this.treasureFound = new ArrayList<>();
        this.maxPower = maxPower;
        this.power = maxPower;
        this.gold = 0;
        this.inventory = new HashMap<>();
        this.equippedArmor = null;
        this.equippedWeapon = null;
    }

    public void applyStatusEffect(StatusEffects effect, int turns) {
        this.statusEffects.put(effect, turns);
    }

    protected void removeStatusEffect(StatusEffects effect) {
        this.statusEffects.remove(effect);
    }

    public void removeAllStatusEffects() {
        this.statusEffects.clear();
    }

    public int getStatusEffectDuration(StatusEffects effect) {
        return this.statusEffects.getOrDefault(effect, 0);
    }

    public Map<StatusEffects, Integer> getStatusEffects() {
        return this.statusEffects;
    }

    public boolean isStatusEffectsEmpty() {
        return this.statusEffects.isEmpty();
    }

    public void updateStatusEffects() {
        ArrayList<StatusEffects> toRemove = new ArrayList<>();

        for (var effect : this.statusEffects.entrySet()) {
            StatusEffects statusEffect = effect.getKey();
            int turnsLeft = effect.getValue() - 1;

            switch (statusEffect) {
                case STRENGTH, INVISIBILITY, VANISH -> { /* nothing needed */ }
                case HEALING -> this.heal(2);
                case SHIELD -> this.defenceMultiplier += 0.5;
            }
            this.statusEffects.put(statusEffect, turnsLeft);
            if (turnsLeft == 0) {
                toRemove.add(statusEffect);
            }
        }

        toRemove.forEach(this.statusEffects::remove);
    }


    protected void setDamageMultiplier(double damageMultiplier) {
        this.damageMultiplier = damageMultiplier;
    }

    public List<RoomType> getCompletedRooms() {
        return this.completedRooms;
    }

    public void addCompletedRoom(RoomType roomType) {
        this.completedRooms.add(roomType);
    }

    public List<RoomType> getTreasureFound() {
        return this.treasureFound;
    }

    public boolean addTreasureFound(RoomType roomType) {
        return this.treasureFound.add(roomType);
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

//    public HashMap<Item, Integer> getInventory() {
//        return this.inventory;
//    }


    public void showInventory() {
        boolean open = true;
        while (open) {
            System.out.println("\n" + "=== Inventory Menu ===");
            System.out.println("1) Show all items");
            System.out.println("2) Show consumables");
            System.out.println("3) Show weapons");
            System.out.println("4) Show armor");
            System.out.println("0) Exit Inventory");

            int choice = Utility.handleDecision(0, 4);

            switch (choice) {
                case 1 -> this.showInventory(InventoryView.ALL);
                case 2 -> this.showInventory(InventoryView.CONSUMABLES);
                case 3 -> this.showInventory(InventoryView.WEAPONS);
                case 4 -> this.showInventory(InventoryView.ARMOR);
                case 0 -> open = false;
                default -> System.out.println("Invalid selection. Try again");
            }
        }
    }

    private void showInventory(InventoryView view) {
        boolean found = false;

        System.out.println("\n" + "Inventory:");

        for (var item : this.inventory.keySet()) {

            switch (view) {
                case ALL -> {
                    System.out.println(item.getName() + " (" + item.getType() + ")");
                    found = true;
                }
                case CONSUMABLES -> {
                    if (item.getType() == ItemType.POTION) {
                        System.out.println(item.getName() + " (" + item.getValue() + " HP)");
                        found = true;
                    }
                }
                case WEAPONS -> {
                    if (item.getType() == ItemType.WEAPON) {
                        System.out.println(item.getName() + " (" + item.getValue() + " damage)");
                        found = true;
                    }
                }
                case ARMOR -> {
                    if (item.getType() == ItemType.ARMOR) {
                        System.out.println(item.getName() + " (" + item.getValue() + " defense)");
                        found = true;
                    }
                }

                default -> System.out.println("No item found.");
            }
            if (!found) {
                System.out.println("No item found.");
            }
        }
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
        return (int)((this.getAttack() + (this.equippedWeapon != null ? this.equippedWeapon.getValue() : 0)) * this.damageMultiplier);
    }

    @Override
    public int getTotalDefense() {
        return (int)((this.getDefence() + (this.equippedArmor != null ? this.equippedArmor.getValue() : 0)) * this.defenceMultiplier);
    }

    public GameState handleStats() {
        System.out.println("\n=== STATS ===");
        this.displayStats();

        Utility.enterToContinue();
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
        System.out.println("║ Weapon:  " + (this.getEquippedWeapon() == null ? "None" : this.getEquippedWeapon().getName() + " + " + this.getEquippedWeapon().getValue() + " Damage"));
        System.out.println("╚═════════════════════════════════╝");
    }

    public abstract String getPowerString();

    public abstract PlayerClass getClassType();

    public abstract void performeUtilityAbitlity();

    public abstract void beforeTurn();

    public boolean isUntargatable() {
        return false;
    }
}
