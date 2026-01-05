package model;

import model.enums.type.ItemType;

public record Item(String name, ItemType type, int value) {

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
