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

    public String getName() {
        return this.name;
    }

    public ItemType getType() {
        return this.type;
    }

    public int getValue() {
        return this.value;
    }

    //TODO: Checkstyle + switch
    public void displayInfo() {
        String typeStr = "";

        switch (this.type) {
            case WEAPON -> typeStr = "Weapon (+" + this.value + " damage)";
            case ARMOR -> typeStr = "Armor (+" + this.value + " defense)";
            case POTION -> typeStr = "Potion (+" + this.value + " HP)";
            case TREASURE -> typeStr = "Treasure (" + this.value + " gold)";
        }

        System.out.println("  " + this.name + " [" + typeStr + "]");
    }
}
