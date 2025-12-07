package game.room.instance;


import data.ItemDatabase;
import game.room.RoomOutCome;
import game.room.combat.CombatSystem;
import model.Enemy;
import model.Item;
import model.Player;
import model.enums.room.RoomMap;
import model.enums.room.RoomResult;
import model.enums.room.RoomType;
import utility.Utility;

import java.util.ArrayList;

import static utility.Utility.handleDecision;

public class Room {
    private RoomType roomType;
    private Player player;
    private Boolean playerHasTreasure;
    private CombatSystem combat;
    private RoomMap display;
    private ArrayList<Enemy> enemies;
    private ItemDatabase itemDatabase;
    private Boolean isTreasurePresent;

    public Room(RoomType roomType, Player player, CombatSystem combat, RoomMap display, ArrayList<Enemy> enemies, Boolean treasurePresent) {
        this.roomType = roomType;
        this.player = player;
        this.playerHasTreasure = this.player.getTreasureFound().contains(roomType);

        this.combat = combat;
        this.display = display;
        this.enemies = new ArrayList<>();
        this.itemDatabase = ItemDatabase.getInstance(player);

        this.isTreasurePresent = treasurePresent;

        if (enemies != null) {
            this.enemies.addAll(enemies);
        }
    }

    private Item getTreasure() {
        return this.itemDatabase.getRandomItem();
    }

    /**
     * Method to handle the room interaction.
     * Output of the description and map of the room with available options.
     * Checks if the player has already cleared the room (Enemies and Treasure),
     * if yes, he is redirected to the next room.
     * If no, then he needs to clear the room.
     *
     * @return
     */
    // TODO: Refactor this method -> too big for one method ( nesting )
    public RoomOutCome enter() {
        while (true) {
            this.showRoomInfo();

            if (this.player.getCompletedRooms().contains(this.roomType) && this.playerHasTreasure) {

                System.out.println("\n" + "You have already cleared this room.");
                System.out.println("\n" + "Move to another room...");
                Utility.enterToContinue();
                return new RoomOutCome(RoomResult.COMPLETED, RoomType.TWO);
            }

            System.out.println("1. Investigate 'X' marking " + (this.allEnemiesKilled() ? "(cleared)" : ""));

            int choice = this.userInput();

            switch (choice) {
                case 0 -> { // Back to menu
                    if (this.allEnemiesKilled() && this.isTreasureFound()) {
                        if (this.player.getCompletedRooms().contains(this.roomType)) {
                            return new RoomOutCome(RoomResult.EXIT, null); // back to menu
                        }
                        this.player.addCompletedRoom(this.roomType);
                    }
                    return new RoomOutCome(RoomResult.EXIT, null); // back to menu
                }
                case 1 -> { // Enemy
                    if (!this.allEnemiesKilled()) {
                        if (this.combat.startCombat(this.player, this.enemies.getFirst())) {
                            this.enemies.removeFirst();
                        } else {
                            return new RoomOutCome(RoomResult.DEATH, null);
                        }
                    } else {
                        System.out.println("\n" + "All enemies has been defeated.");
                        Utility.enterToContinue();
                    }
                }
                case 2 -> { // Treasure
                    this.treasureFound();
                }
                case 3 -> { // Proceed to the next room
                    this.player.addCompletedRoom(this.roomType);
                    return new RoomOutCome(RoomResult.COMPLETED, RoomType.TWO);
                }
                default -> {
                    System.out.println("Invalid selection. Try again");
                    Utility.enterToContinue();
                }
            }
        }
    }

    private void showRoomInfo() {
        System.out.println("\n" + this.display.getDescription());
        System.out.println("╔═════════════════════╗");
        System.out.println("║ You are in room " + this.roomType.name() + " ║");

        System.out.println(this.display.getMap());
    }

    private boolean allEnemiesKilled() {
        return this.enemies.isEmpty();
    }

    private boolean isTreasureFound() {
        return this.playerHasTreasure;
    }


    private void treasureFound() {
        if (this.isTreasureFound()) {
            System.out.println("\n" + "You already found this treasure.");
            Utility.enterToContinue();
        } else {
            this.playerHasTreasure = this.player.addTreasureFound(this.roomType);

            Item item = this.getTreasure();
            if (item == null) {

                return;
            }
            this.player.addItem(item);
            this.itemOutput(item);

            Utility.enterToContinue();
        }
    }

    private void itemOptions(Item item) {
        switch (item.getType()) {
            case WEAPON, ARMOR -> {
                System.out.println("1. Equip");
                System.out.println("2. Keep");
            }
            case POTION -> {
                System.out.println("1. Use");
                System.out.println("2. Keep");
            }
        }

        switch (handleDecision(1, 2)) {
            case 1 -> {
                switch (item.getType()) {
                    case WEAPON -> {
                        this.player.setEquippedWeapon(item);
                        System.out.println("\n" + "Item has been equipped.");
                    }
                    case ARMOR -> {
                        this.player.setEquippedArmor(item);
                        System.out.println("\n" + "Item has been equipped.");
                    }
                    case POTION -> {
                        this.player.restorePower(item.getValue());
                        System.out.println("\n" + "Potion has been used.");
                    }
                }
            }
            case 2 -> {
                item.displayInfo();
            }
        }
    }

    private void itemOutput(Item item) {
        System.out.println("\n" + "You have found");
        item.displayInfo();
        this.itemOptions(item);
    }

    private int userInput() {
        int maxChoice = 1;
        if (this.isTreasurePresent) {
            maxChoice = 2;
            System.out.println("2. Investigate '?' marking " + (this.isTreasureFound() ? "(found)" : ""));
        }

        if (this.allEnemiesKilled()) {
            maxChoice = 3;
            System.out.println("3. Proceed to next room");
            System.out.println("0. Back to menu");
            return handleDecision(0, maxChoice);
        }

        System.out.println("0. Back to menu");
        return handleDecision(0, maxChoice);
    }
}
