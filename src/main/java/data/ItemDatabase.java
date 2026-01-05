package data;

import model.Item;
import model.Player;
import utility.Utility;

import java.util.ArrayList;
import java.util.Random;

/**
 * Singleton class responsible for managing and storing items in the game.
 * Possible change to normal class in the future.
 * If adding multiple players, this will be a problem as
 * each player should have their own loot table.
 */
public class ItemDatabase {
    private static ItemDatabase instance;

    private final ArrayList<Item> lootTable;
    private final Random random;

    private ItemDatabase(Player player) {
        this.random = new Random();
        this.lootTable = new ArrayList<>(Items.getAvailableLoot(player));
    }

    /**
     * Provides access to the singleton instance of the ItemDatabase class.
     * If an instance does not already exist, it initializes the ItemDatabase
     * with the provided player and returns it.
     */
    public static ItemDatabase getInstance(Player player) {
        if (instance == null) {
            instance = new ItemDatabase(player);
        }
        return instance;
    }

    /**
     * Gets a random item from the loot table.
     * If the table is empty, it returns null and notices the user.
     */
    public Item getRandomItem() {
        if (this.lootTable.isEmpty()) {
            System.out.println("\n" + "No items in loot table!");
            Utility.enterToContinue();
            return null;
        }
        int index = this.random.nextInt(this.lootTable.size());
        Item item = this.lootTable.get(index);
        this.lootTable.remove(index);
        return item;
    }

}
