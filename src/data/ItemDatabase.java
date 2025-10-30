package data;

import model.Item;
import model.enums.ItemType;

public class ItemDatabase {
    public static final Item WOODEN_STAFF = new Item("Wooden Staff", ItemType.WEAPON, 4 );
    public static final Item IRON_DAGGER = new Item("Iron Dagger", ItemType.WEAPON, 5 );
    public static final Item IRON_SWORD = new Item("Iron Sword", ItemType.WEAPON, 3 );

}
