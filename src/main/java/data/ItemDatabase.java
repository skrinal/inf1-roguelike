package data;

import model.Item;
import model.Player;
import model.enums.type.ItemType;
import utility.Utility;

import java.util.ArrayList;
import java.util.Random;

public class ItemDatabase {
    private static ItemDatabase instance;

    private final ArrayList<Item> lootTable;
    private final Random random;

    private ItemDatabase(Player player) {

        this.random = new Random();
        this.lootTable = new ArrayList<>();

        switch (player.getCombatTag()) {
            case MAGE -> {
                this.lootTable.add(new Item("Wooden Staff", ItemType.WEAPON, 4));
                //this.lootTable.add(new Item("Magic Wand", ItemType.WEAPON, 6));
                //this.lootTable.add(new Item("Robe of Sparks", ItemType.ARMOR, 3));
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
            instance = new ItemDatabase(player);
        }
        return instance;
    }

    /**
     * Gets a random item from the loot table.
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
