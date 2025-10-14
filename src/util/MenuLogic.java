package util;

import model.Item;
import model.enums.PlayerClass;
import model.players.Mage;
import model.players.Rogue;
import model.players.Warrior;

import java.util.Scanner;

import static util.GameStrings.GAME_TITLE;

public class MenuLogic {

    public static model.Player handleCharacterCreation(Scanner input) throws InterruptedException {
        System.out.println("\n=== CHARACTER CREATION ===\n");
        System.out.print("Name your character: ");
        String name = input.nextLine();
        PlayerClass playerClass = selectedClass(input);

        return createCharacter(playerClass, name);
    }

    public static int showGameMenu(Scanner input, model.Player player) {
        while (true) {
            System.out.println("\n=== GAME MENU ===");
            System.out.println("1. Dungeon");
            System.out.println("2. Random Labyrinth - X");
            System.out.println("3. Inventory - X");
            System.out.println("4. Stats");
            System.out.println("0. Back");
            System.out.print("Select: ");

            if (!input.hasNextInt()) {
                System.out.println("Invalid selection. Try again");
                input.next();
                continue;
            }
            int choice = input.nextInt();
            input.nextLine();

            if (choice >= 0 && choice <= 4) {
                return choice;
            }
            System.out.println("Invalid selection. Try again");
        }
    }

    public static int showMainMenu(Scanner input) {
        while (true) {
            printGameMenu();
            if (!input.hasNextInt()) {
                IO.println("Invalid selection. Try again");
                input.next();
                continue;
            }
            int choice = input.nextInt();
            input.nextLine();
            if (choice >= 0 && choice <= 2) return choice;
            IO.println("Invalid selection");
        }
    }

    public static int showInventoryMenu(Scanner input, model.Player player) {
        while (true) {
            IO.println("\n=== INVENTORY ===");
            IO.println("Gold : " + player.getGold());
            IO.println("\nEquipped:");
            IO.println("  Weapon: " + (player.getEquippedWeapon() == null ? "None" : player.getEquippedWeapon().getName()));
            IO.println("  Armor: " + (player.getEquippedArmor() == null ? "None" : player.getEquippedArmor().getName()));

            IO.println("\nItems:");
            if (player.getInventory().isEmpty()) {
                IO.println(" (empty)");
                IO.println("0. Back");
                IO.print("Select: ");
                if (!input.hasNextInt()) {
                    input.next();
                    continue;
                }
                int choice = input.nextInt();
                input.nextLine();
                if (choice == 0) return 0;
                continue;
            }
            int index = 1;
            Item[] items = new Item[player.getInventory().size()];
            for (var entry : player.getInventory().entrySet()) {
                Item item = entry.getKey();
                int count = entry.getValue();
                String equipped = "";
                if (item.equals(player.getEquippedWeapon()) || item.equals(player.getEquippedArmor())) {
                    equipped = " [EQUIPPED]";
                }
                IO.println(index + ". " + item.getName() + " x" + count + equipped);
                items[index - 1] = item;
                index++;
            }
            IO.println("0. Back");
            IO.print("Select item: ");

            if (!input.hasNextInt()) {
                IO.println("Invalid selection. Try again");
                input.next();
                continue;
            }
            int choice = input.nextInt();
            input.nextLine();

            if (choice == 0) {
                return 0;
            } else if (choice >= 1 && choice <= items.length) {
                Item selectedItem = items[choice - 1];
                handleItemDetail(input, player, selectedItem);
            } else {
                IO.println("Invalid selection. Try again");
            }
        }
    }

    private static void handleItemDetail(Scanner input, model.Player player, Item item) {
        while (true) {
            IO.println("\n=== ITEM DETAILS ===");
            IO.println("Name: " + item.getName());
            IO.println("Type: " + item.getType());
            String statDes = switch (item.getType()) {
                case WEAPON -> " damage";
                case ARMOR -> " defense";
                case POTION -> " health";
                case TREASURE -> " gold";
            };
            IO.println("Value: " + item.getValue() + statDes);
            IO.println("Quantity: " + player.getInventory().get(item));

            boolean isEquipped = item.equals(player.getEquippedWeapon()) || item.equals(player.getEquippedArmor());
            if (isEquipped) {
                IO.println("Status: EQUIPPED");
            }

            IO.println("\n1. Equip" + (isEquipped ? " (already equipped)" : ""));
            IO.println("2. Drop/Sell");
            IO.println("3. Back to items");
            IO.print("Select: ");

            if (!input.hasNextInt()) {
                IO.println("Invalid selection. Try again");
                input.next();
                continue;
            }
            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1 -> {
                    if (item.getType().equals("Weapon")) {
                        player.setEquippedWeapon(item);
                        IO.println("Equipped: " + item.getName());
                    } else if (item.getType().equals("Armor")) {
                        player.setEquippedArmor(item);
                        IO.println("Equipped: " + item.getName());
                    } else {
                        IO.println("Cannot equip this item type");
                    }
                }
                case 2 -> {
                    if (dropItem(player, item)) {
                        IO.println("Dropped: " + item.getName());
                        return;
                    }
                }
                case 3 -> {
                    return;
                }
                default -> IO.println("Invalid selection. Try again");
            }
        }
    }

    private static boolean dropItem(model.Player player, Item item) {
        int count = player.getInventory().get(item);

        if (count > 1) {
            player.getInventory().put(item, count - 1);
            return true;
        } else {
            player.getInventory().remove(item);
            if (item.equals(player.getEquippedWeapon())) {
                player.setEquippedWeapon(null);
            }
            if (item.equals(player.getEquippedArmor())) {
                player.setEquippedArmor(null);
            }
            return true;
        }
    }

//    public static int dshowInventoryMenu(Scanner input, model.Player player) {
//        while (true) {
//            System.out.println("\n=== INVENTORY ===");
//            System.out.println("Gold: " + player.getGold());
//            System.out.println("\nEquipped:");
//            System.out.println("  Weapon: " + (player.getEquippedWeapon() == null ? "None" : player.getEquippedWeapon().getName()));
//            System.out.println("  Armor: " + (player.getEquippedArmor() == null ? "None" : player.getEquippedArmor().getName()));
//
//            System.out.println("\nItems:");
//            if (player.getInventory().isEmpty()) {
//                System.out.println("  (empty)");
//            } else {
//                int index = 1;
//                for (var entry : player.getInventory().entrySet()) {
//                    System.out.println("  " + index + ". " + entry.getKey().getName() +
//                                     " x" + entry.getValue() + " (" + entry.getKey().getType() + ")");
//                    index++;
//                }
//            }
//
//            System.out.println("\n1. Equip item");
//            System.out.println("2. Drop item");
//            System.out.println("3. Back");
//            System.out.print("Select: ");
//
//            if (!input.hasNextInt()) {
//                System.out.println("Invalid selection. Try again");
//                input.next();
//                continue;
//            }
//            int choice = input.nextInt();
//            input.nextLine();
//
//            if (choice == 3) {
//                return 3; // Return to game menu
//            } else if (choice == 1) {
//                handleEquipItem(input, player);
//            } else if (choice == 2) {
//                handleDropItem(input, player);
//            } else {
//                System.out.println("Invalid selection. Try again");
//            }
//        }
//    }

//    private static void handleEquipItem(Scanner input, model.Player player) {
//        if (player.getInventory().isEmpty()) {
//            System.out.println("No items to equip!");
//            return;
//        }
//
//        System.out.print("Enter item number to equip: ");
//        if (!input.hasNextInt()) {
//            System.out.println("Invalid selection");
//            input.next();
//            return;
//        }
//        int itemIndex = input.nextInt();
//        input.nextLine();
//
//        if (itemIndex < 1 || itemIndex > player.getInventory().size()) {
//            System.out.println("Invalid item number");
//            return;
//        }
//
//        Item selectedItem = (Item) player.getInventory().keySet().toArray()[itemIndex - 1];
//
//        if (selectedItem.getType().equals("Weapon")) {
//            player.setEquippedWeapon(selectedItem);
//            System.out.println("Equipped: " + selectedItem.getName());
//        } else if (selectedItem.getType().equals("Armor")) {
//            player.setEquippedArmor(selectedItem);
//            System.out.println("Equipped: " + selectedItem.getName());
//        } else {
//            System.out.println("Cannot equip this item type");
//        }
//    }
//
//    private static void handleDropItem(Scanner input, model.Player player) {
//        if (player.getInventory().isEmpty()) {
//            System.out.println("No items to drop!");
//            return;
//        }
//
//        System.out.print("Enter item number to drop: ");
//        if (!input.hasNextInt()) {
//            System.out.println("Invalid selection");
//            input.next();
//            return;
//        }
//        int itemIndex = input.nextInt();
//        input.nextLine();
//
//        if (itemIndex < 1 || itemIndex > player.getInventory().size()) {
//            System.out.println("Invalid item number");
//            return;
//        }
//
//        Item selectedItem = (Item) player.getInventory().keySet().toArray()[itemIndex - 1];
//        int count = player.getInventory().get(selectedItem);
//
//        if (count > 1) {
//            player.getInventory().put(selectedItem, count - 1);
//        } else {
//            player.getInventory().remove(selectedItem);
//            if (selectedItem.equals(player.getEquippedWeapon())) {
//                player.setEquippedWeapon(null);
//            }
//            if (selectedItem.equals(player.getEquippedArmor())) {
//                player.setEquippedArmor(null);
//            }
//        }
//        System.out.println("Dropped: " + selectedItem.getName());
//    }

    private static void printGameMenu() {
        System.out.println(GAME_TITLE);
        System.out.println("==========================");
        System.out.println("1. Start Game");
        System.out.println("2. Settings - Not implemented yet");
        System.out.println("3. Exit");
        System.out.print("Select: ");
    }

    private static void clearConsole() {
        for (int i = 0; i < 15; i++) {
            System.out.println();
        }
    }

    private static model.Player createCharacter(PlayerClass playerClass, String name) {
        return switch (playerClass) {
            case WARRIOR -> new Warrior(name);
            case MAGE -> new Mage(name);
            case ROGUE -> new Rogue(name);
        };
    }

    private static PlayerClass selectedClass(Scanner input) {
        while (true) {
            System.out.println("\n1. Warrior");
            System.out.println("2. Mage");
            System.out.println("3. Rogue");
            System.out.print("Choose a class: ");

            if (!input.hasNextInt()) {
                System.out.println("Invalid selection. Try again");
                input.next();
                continue;
            }
            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1 -> {
                    return PlayerClass.WARRIOR;
                }
                case 2 -> {
                    return PlayerClass.MAGE;
                }
                case 3 -> {
                    return PlayerClass.ROGUE;
                }
                default -> {
                    System.out.println("Invalid selection. Try again");
                }
            }
        }
    }


}
