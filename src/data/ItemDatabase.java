package data;

import model.Item;
import model.Player;
import model.enums.ItemType;

import java.util.ArrayList;
import java.util.Random;

public class ItemDatabase {
    private final ArrayList<Item> lootTable;
    private final Random random;

    /**
     * Constructs a new ItemDatabase with a loot table based on the player's class.
     * @param player
     */
    public ItemDatabase(Player player) {

        this.random = new Random();
        this.lootTable = new ArrayList<>();

        switch (player.getClassType()) {
            case MAGE -> {
                lootTable.add(new Item("Wooden Staff", ItemType.WEAPON, 4));
                lootTable.add(new Item("Magic Wand", ItemType.WEAPON, 6));
                lootTable.add(new Item("Robe of Sparks", ItemType.ARMOR, 3));
            }
            case ROGUE -> {
                lootTable.add(new Item("Iron Dagger", ItemType.WEAPON, 5));
                lootTable.add(new Item("Shadow Blade", ItemType.WEAPON, 7));
            }
            case WARRIOR -> {
                lootTable.add(new Item("Iron Sword", ItemType.WEAPON, 3));
                lootTable.add(new Item("Steel Axe", ItemType.WEAPON, 7));
                lootTable.add(new Item("Chainmail", ItemType.ARMOR, 5));
            }
        }
    }

    /**
     * Get's a random item from the loot table.
     */
    public Item getRandomItem() {
        if (lootTable.isEmpty()) {
            System.out.println("No items in loot table!");
            return null;
        }
        int index = random.nextInt(lootTable.size());
        Item item = lootTable.get(index);
        lootTable.remove(index);
        return item;
    }

}
