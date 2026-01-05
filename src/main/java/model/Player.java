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

/**
 * The Player class represents a playable character in the game.
 * It extends the Character class and introduces additional properties and methods
 * specific to player actions and progression within the game.
 */
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

    private final int boxWidth = 44;


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

    /**
     * Heals the player by 5 + level and restores 15 power.
     * Method is used as the 4th option in the combat menu.
     */
    public void resting() {
        this.heal(5 + this.getLevel());
        this.restorePower(15);
    }

    protected int getExperience() {
        return this.experience;
    }

    protected int getExperienceToNextLevel() {
        return this.experienceToNextLevel;
    }

    /**
     * Method to gain experience.
     * Which runs levelUp() method in While function till there is not enough experience
     * to next level.
     */
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

        int oldMaxHp = this.getMaxHp();
        int oldAttack = this.getAttack();
        int oldDefence = this.getDefence();


        this.setMaxHp(this.levelUpHp());
        this.setHp(this.resetHp());
        this.setAttack(this.levelUpAttack());
        this.setDefence(this.levelUpDefence());
        this.power = this.resetPower();

        this.levelUpOutput(oldMaxHp, oldAttack, oldDefence);
    }

    private void levelUpOutput(int oldMaxHp, int oldAttack, int oldDefence) {
        String level = "You have leveled up to level " + this.getLevel() + "!";
        String hp = "HP: " + oldMaxHp + " -> " + this.getMaxHp();
        String attack = "Attack: " + oldAttack + " -> " + this.getAttack();
        String defence = "Defence: " + oldDefence + " -> " + this.getDefence();

        System.out.println("╔" + "═".repeat(this.boxWidth) + "╗");

        this.printCenter("", this.boxWidth);
        this.printCenter(level, this.boxWidth);
        this.printCenter("", this.boxWidth);
        this.printCenter(hp, this.boxWidth);
        this.printCenter(attack, this.boxWidth);
        this.printCenter(defence, this.boxWidth);
        this.printCenter("", this.boxWidth);

        System.out.println("╚" + "═".repeat(this.boxWidth) + "╝");

        this.pause();
    }

    private void printCenter(String text, int width) {
        int padding = width - text.length();
        int left = padding / 2;
        int right = padding - left;

        this.print("║" + " ".repeat(left) + text + " ".repeat(right) + "║");
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

    /**
     * Returns player's completed rooms.
     */
    public List<RoomType> getCompletedRooms() {
        return this.completedRooms;
    }

    /**
     * Adds room to completed rooms.
     */
    public void addCompletedRoom(RoomType roomType) {
        this.completedRooms.add(roomType);
    }

    /**
     * Returns player's treasure found.
     */
    public List<RoomType> getTreasureFound() {
        return this.treasureFound;
    }

    /**
     * Adds room to treasure found.
     */
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

    /**
     * Displays the player's inventory menu and allows the player to interact with categorized items.
     * The player can choose to view and handle items from specific categories such as consumables, weapons, or armor.
     * Choosing an option triggers the use or equipment of items.
     * The inventory menu remains open until the player chooses to exit.
     * Returns always returns true as interacting with the inventory does not consume a turn.
     */
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

    /**
     * Adds item to inventory.
     */
    public void addItem(Item item) {
        this.inventory.put(item, this.inventory.getOrDefault(item, 0) + 1);
    }

    /**
     * Restores power by amount.
     */
    public void restorePower(int amount) {
        this.power = Math.min(this.maxPower, this.power + amount);
    }

    /**
     * Checks if player has enough power to use ability.
     * If yes remove the power from player and return true.
     * If no return false.
     */
    public boolean usePower(int amount) {
        if (this.power >= amount) {
            this.power -= amount;
        } else {
            return false;
        }
        return true;
    }

    /**
     * Returns a visual bar representing the player's current power.
     */
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

    /**
     * OutPuts player's stats and waits for the player to press enter
     * to continue to the game.
     */
    public GameState handleStats() {
        this.print(PlayerStrings.PLAYER_STATS);
        this.displayStats();

        this.pause();
        return GameState.GAME;
    }

    /**
     * Restores player's max power to its current value.
     */
    public void restoreMaxPower() {
        this.power = this.maxPower;
    }

    /**
     * Return current equipped weapon.
     */
    public Item getEquippedWeapon() {
        return this.equippedWeapon;
    }

    /**
     * Sets Item from parameter as an equipped weapon.
     */
    public void setEquippedWeapon(Item equippedWeapon) {
        this.equippedWeapon = equippedWeapon;
    }

    /**
     * Return current equipped armor.
     */
    public Item getEquippedArmor() {
        return this.equippedArmor;
    }

    /**
     * Sets Item from parameter as an equipped armor.
     */
    public void setEquippedArmor(Item equippedArmor) {
        this.equippedArmor = equippedArmor;
    }

    /**
     * Displays the current statistics of the player in a formatted layout.
     * The displayed information includes:
     * - Player's name and combat tag
     * - Current and maximum HP (health points) along with visual representation
     * - Current and maximum power along with visual representation and power type
     * - Total attack and defense values, considering equipped items
     * - Current amount of gold
     * - Player's level and experience progress toward the next level
     * - Details of equipped armor and weapon, showing item names and respective bonuses
     */
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

    /**
     * Determines if the player can be targeted by a specific enemy attacker.
     */
    public boolean canBeTargetedBy(Enemy attacker) {
        if (this.hasStatusEffect(StatusEffects.INVISIBILITY)) {
            return attacker.canTargetInvisible();
        }
        return false;
    }

    /**
     * Returns if the player is untargetable.
     */
    public boolean isUntargetable() {
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
