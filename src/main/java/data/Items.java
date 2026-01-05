package data;

import model.Item;
import model.Player;
import model.enums.CombatTag;
import model.enums.type.ItemType;

import java.util.ArrayList;
import java.util.List;

/**
 * Enum class representing all items that can be picked up by the player.
 */
public enum Items {
    //GENERAL POTIONS
    HEALTH_CHALICE("Health Chalice", ItemType.POTION, 100, null),
    HEALTH_VIAL("Health Vial", ItemType.POTION, 50, null),
    HEALTH_POTION("Health Potion", ItemType.POTION, 25, null),

    //MAGE
    MAGIC_STAFF("Magic Wand", ItemType.WEAPON, 6, CombatTag.MAGE),
    WOODEN_STAFF("Wooden Staff", ItemType.WEAPON, 3, CombatTag.MAGE),

    ROBE_OF_SPARKS("Robe of Sparks", ItemType.ARMOR, 4, CombatTag.MAGE),
    WOOL_ROBE("Wool Robe", ItemType.ARMOR, 2, CombatTag.MAGE),

    //WARRIOR
    STEEL_AXE("Steel Axe", ItemType.WEAPON, 7, CombatTag.WARRIOR),
    IRON_SWORD("Iron Sword", ItemType.WEAPON, 5, CombatTag.WARRIOR),

    CHAINMAIL_ARMOR("Chainmail Armor", ItemType.ARMOR, 6, CombatTag.WARRIOR),
    LEATHER_ARMOR("Leather Armor", ItemType.ARMOR, 3, CombatTag.WARRIOR),

    //ROGUE
    SHADOW_BLADE("Shadow Blade", ItemType.WEAPON, 9, CombatTag.ROGUE),
    IRON_DAGGER("Iron Dagger", ItemType.WEAPON, 4, CombatTag.ROGUE),

    NIGHTSCALE_ARMOR("Nightscale Armor", ItemType.ARMOR, 7, CombatTag.ROGUE),
    LEATHER_VEST("Leather Vest", ItemType.ARMOR, 2, CombatTag.ROGUE);




    private final Item item;
    private final CombatTag requiredTag;
    Items(String name, ItemType itemType, int value, CombatTag tag) {
        this.item = new Item(name, itemType, value);
        this.requiredTag = tag;
    }

    /**
     * Returns a list of all items that are available for the player to pick up.
     * Depending on his combat tag, only weapons and armor of the same tag are available.
     */
    public static List<Item> getAvailableLoot(Player player) {
        List<Item> available = new ArrayList<>();

        for (Items item : Items.values()) {
            if (item.item.type() == ItemType.WEAPON || item.item.type() == ItemType.ARMOR) {
                if (item.requiredTag == player.getCombatTag()) {
                    available.add(item.getItem());
                }
            }
        }

        return available;
    }

    /**
     * Returns the item object.
     */
    public Item getItem() {
        return this.item;
    }
}
