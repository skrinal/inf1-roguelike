package model;

import model.enums.GameState;
import model.enums.type.ItemType;
import model.enums.room.RoomType;
import model.enums.status.StatusEffects;
import model.strings.PlayerStrings;
import utility.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public abstract class Player extends Character {
    private final int maxPower;
    private int power;
    private int gold;

    private final HashMap<Item, Integer> inventory;

    private List<RoomType> completedRooms;
    private List<RoomType> treasureFound;

    private Item equippedWeapon;
    private Item equippedArmor;

    private int experience;
    private int experienceToNextLevel;


    protected Player(String name, int maxHp, int attack, int defence, int maxPower, int level) {
        super(name, maxHp, attack, defence, level);
        this.completedRooms = new ArrayList<>();
        this.treasureFound = new ArrayList<>();
        this.maxPower = maxPower;
        this.power = maxPower;
        this.gold = 0;
        this.inventory = new HashMap<>();
        this.equippedArmor = null;
        this.equippedWeapon = null;

        this.experience = 0;
        this.experienceToNextLevel = this.calculateExperienceToNextLevel();
    }

    protected int getExperience() {
        return this.experience;
    }

    protected int getExperienceToNextLevel() {
        return this.experienceToNextLevel;
    }

    public void gainExperience(int amount) {
        this.experience += amount;
        while (this.experience >= this.experienceToNextLevel) {
            this.levelUp();
        }
    }

    private void levelUp() {
        this.experience -= this.experienceToNextLevel;
        this.incrementLevel();
        this.experienceToNextLevel = this.calculateExperienceToNextLevel();

        this.setMaxHp(this.levelUpHp());
        this.setHp(this.resetHp());
        this.setAttack(this.levelUpAttack());
        this.setDefence(this.levelUpDefence());
        this.power = this.resetPower();
    }

    private int levelUpHp() {
        return this.getMaxHp() + (10 + (this.getLevel() * 2));
    }

    private int resetHp() {
        return this.getMaxHp();
    }

    private int resetPower() {
        return this.getMaxPower();
    }

    private int levelUpAttack() {
        return this.getAttack() + 2 + this.getLevel();
    }

    private int levelUpDefence() {
        return this.getDefence() + (int)((double)this.getLevel() / 2);
    }

    private int calculateExperienceToNextLevel() {
        return (int)(100 * Math.pow(1.2, this.getLevel()));
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

    public boolean showInventory() {
        boolean open = true;

        while (open) {
            this.print(PlayerStrings.PLAYER_INVENTORY_MENU);

            int choice = Utility.handleDecision(0, 3);

            switch (choice) {
                case 1 -> this.handleItemSelection(ItemType.POTION);
                case 2 -> this.handleItemSelection(ItemType.WEAPON);
                case 3 -> this.handleItemSelection(ItemType.ARMOR);
                case 0 -> open = false;
            }
        }
        // Inventory never consumes a turn
        return true;
    }

    private void handleItemSelection(ItemType type) {
        List<Item> items = new ArrayList<>();

        for (var entry : this.inventory.entrySet()) {
            if (entry.getKey().type() == type) {
                items.add(entry.getKey());
            }
        }

        if (items.isEmpty()) {
            this.print("You don't have any " + type.toString().toLowerCase() + "s!");
            this.print("You don't have any " + type.toString().toLowerCase() + "s!");
            this.pause();
            return;
        }

        this.print("Choose an item:");
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            this.print((i + 1) + ") " + item.name());
        }
        this.print("0) Exit");

        int choice = Utility.handleDecision(0, items.size());
        if (choice == 0 ) {
            return;
        }

        Item selected = items.get(choice - 1);

        this.useOrEquipItem(selected);
    }

    private void useOrEquipItem(Item item) {
        switch (item.type()) {
            case POTION -> {
                this.heal(item.value());
                this.consumeItem(item);
                this.print("You used " + item.name() + "!");
                this.pause();
            }
            case WEAPON -> {
                this.setEquippedWeapon(item);
                this.print("You equipped " + item.name() + "!");
                this.pause();
            }
            case ARMOR -> {
                this.setEquippedArmor(item);
                this.print("You equipped " + item.name() + "!");
                this.pause();
            }
        }
    }



    private void consumeItem(Item item) {
        int amount = this.inventory.get(item);

        if (amount <= 1) {
            this.inventory.remove(item);
        } else {
            this.inventory.put(item, amount - 1);
        }
    }

    //TODO: Refactor later
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
        return (int)((this.getAttack() + (
                this.equippedWeapon != null ? this.equippedWeapon.value() : 0)) * this.getDamageMultiplier());
    }

    @Override
    public int getTotalDefense() {
        return (int)(
                (this.getDefence() + (this.equippedArmor != null ? this.equippedArmor.value() : 0)) * this.getDefenceMultiplier());
    }

    public GameState handleStats() {
        this.print(PlayerStrings.PLAYER_STATS);
        this.displayStats();

        this.pause();
        return GameState.GAME;
    }

    public void restoreMaxPower() {
        this.power = this.maxPower;
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
        this.print("╔═════════════════════════════════╗");
        this.print("║  " + this.getName() + " [" + this.getCombatTag() + "]");
        this.print("╠═════════════════════════════════╣");
        this.print("║ HP:      [" + this.getHealthBar() + "] " + this.getHp() + "/" + this.getMaxHp());
        this.print("║ " + this.getPowerString() + ":    [" + this.getPowerBar() + "] " + this.getPower() + "/" + this.getMaxPower());
        this.print("║ Attack:  " + this.getTotalAttack());
        this.print("║ Defense: " + this.getTotalDefense());
        this.print("║ Gold:    " + this.getGold());
        this.print("║ ");
        this.print("║ Level:   " + this.getLevel() + " - " + this.getExperience() + "/" + this.getExperienceToNextLevel());
        this.print("║ ");
        this.print("║ Armor:   " + (this.getEquippedArmor() == null ? "None" : this.getEquippedArmor().name() + " + " + this.getEquippedArmor().value() + " Armor"));
        this.print("║ Weapon:  " + (this.getEquippedWeapon() == null ? "None" : this.getEquippedWeapon().name() + " + " + this.getEquippedWeapon().value() + " Damage"));
        this.print("╚═════════════════════════════════╝");
    }

    protected void damageAbilitySystemOut(
            String abilityName,
            String actionVerb,
            Character target,
            int damage,
            int rawDamage
    ) {
        String sb = abilityName + "! You " + actionVerb + " " + target.getName() + " for "
                + damage + " damage! (" + rawDamage + " raw)";

        this.print(sb);

        this.pause();
    }

    protected void useAbilitySystemOut(String abilityName) {
        String sb = "You have used " + abilityName + "!";
        
        this.print(sb);
        this.pause();
    }

    protected void useAbilitySystemOut(String abilityName, String actionVerb) {
        String sb = "You have " + actionVerb + " " + abilityName + "!";

        this.print(sb);
        this.pause();
    }

    protected void noPowerSystemOut(String power) {
        String sb = "Not enough " + power + " !";

        this.print(sb);

        this.pause();
    }

    public boolean canBeTargetedBy(Enemy attacker) {
        if (this.hasStatusEffect(StatusEffects.INVISIBILITY)) {
            return attacker.canTargetInvisible();
        }
        return false;
    }

    public boolean isUntargatable() {
        return false;
    }

    public abstract String getPowerString();
    public abstract String getBasicAbilityName();
    public abstract String getSpecialAbilityName();
    public abstract String getUtilityAbilityName();
    public abstract int getBasicAbilityCost();
    public abstract int getSpecialAbilityCost();
    public abstract int getUtilityAbilityCost();
    public abstract void performeUtilityAbility();
    public abstract void beforeTurn();
}
