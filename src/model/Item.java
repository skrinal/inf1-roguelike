package model;

import model.enums.ItemType;

public class Item {
    private String name;
    private ItemType type;
    private int value;

    public Item(String name, ItemType type, int value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public String getName() {return name;}

    public ItemType getType() {return type;}

    public int getValue() {return value;}

    public void displayInfo() {
        String typeStr = "";
        if (type == ItemType.WEAPON) {
            typeStr = "Weapon (+" + value + " damage)";
        } else if (type == ItemType.POTION) {
            typeStr = "Potion (+" + value + " HP)";
        } else if (type == ItemType.ARMOR) {
            typeStr = "Armor (+" + value + " defense)";
        } else if (type == ItemType.TREASURE) {
            typeStr = "Treasure (" + value + " gold)";
        }
        System.out.println("  " + name + " [" + typeStr + "]");
    }
}
