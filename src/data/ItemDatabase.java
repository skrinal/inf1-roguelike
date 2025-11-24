package data;

import model.Item;
import model.Player;
import model.enums.ItemType;

import java.util.ArrayList;
import java.util.Random;

public class ItemDatabase {
    private static ItemDatabase instance;

    private final ArrayList<Item> lootTable;
    private final Random random;

    /**
     * Constructs a new ItemDatabase with a loot table based on the player's class.
     * @param player
     */
    private ItemDatabase(Player player) {

        this.random = new Random();
        this.lootTable = new ArrayList<>();

        switch (player.getClassType()) {
            case MAGE -> {
                this.lootTable.add(new Item("Wooden Staff", ItemType.WEAPON, 4));
                this.lootTable.add(new Item("Magic Wand", ItemType.WEAPON, 6));
                this.lootTable.add(new Item("Robe of Sparks", ItemType.ARMOR, 3));
            }
            case ROGUE -> {
                this.lootTable.add(new Item("Iron Dagger", ItemType.WEAPON, 5));
                this.lootTable.add(new Item("Shadow Blade", ItemType.WEAPON, 7));
            }
            case WARRIOR -> {
                this.lootTable.add(new Item("Iron Sword", ItemType.WEAPON, 3));
                this.lootTable.add(new Item("Steel Axe", ItemType.WEAPON, 7));
                this.lootTable.add(new Item("Chainmail", ItemType.ARMOR, 5));
            }
        }
    }

    public static ItemDatabase getInstance(Player player) {
        if (instance == null) {
            return new ItemDatabase(player);
        } else {
            return instance;
        }
    }

    /**
     * Get's a random item from the loot table.
     */
    public Item getRandomItem() {
        if (this.lootTable.isEmpty()) {
            System.out.println("No items in loot table!");
            return null;
        }
        int index = this.random.nextInt(this.lootTable.size());
        Item item = this.lootTable.get(index);
        this.lootTable.remove(index);
        return item;
    }

}
