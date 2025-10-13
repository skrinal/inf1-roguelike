package model;

import model.enums.ItemType;

public class Item {
    private final String name;
    private final ItemType type;
    private final int value;

    public Item(String name, ItemType type, int value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public String getName() {return this.name;}

    public ItemType getType() {return this.type;}

    public int getValue() {return this.value;}

    public void displayInfo() {
        String typeStr = "";
        if (type == ItemType.WEAPON) {
            typeStr = "Weapon (+" + this.value + " damage)";
        } else if (type == ItemType.POTION) {
            typeStr = "Potion (+" + this.value + " HP)";
        } else if (type == ItemType.ARMOR) {
            typeStr = "Armor (+" + this.value + " defense)";
        } else if (type == ItemType.TREASURE) {
            typeStr = "Treasure (" + this.value + " gold)";
        }
        System.out.println("  " + this.name + " [" + typeStr + "]");
    }
}
