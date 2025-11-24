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
import model.enums.room.TreasureStatus;
import utility.Utility;

import java.util.ArrayList;
import java.util.Scanner;

import static utility.Utility.handleDecision;

public class Room {
    private RoomType roomType;
    private Player player;
    private CombatSystem combat;
    private RoomMap display;
    private ArrayList<Enemy> enemies;
    private ItemDatabase itemDatabase;
    private TreasureStatus treasureState;

    public Room(RoomType roomType, Player player, CombatSystem combat, RoomMap display, ArrayList<Enemy> enemies, Boolean treasurePresent) {
        this.roomType = roomType;
        this.player = player;
        this.combat = combat;
        this.display = display;
        this.enemies = new ArrayList<>();
        this.itemDatabase = ItemDatabase.getInstance(player);

        this.treasureState = treasurePresent ? TreasureStatus.AVAILABLE : TreasureStatus.NONE;

//        if (this.treasureState == null) {
//            this.treasureState = treasurePresent ? TreasureStatus.AVAILABLE : TreasureStatus.NONE;
//        } else {
//            this.treasureState = TreasureStatus.FOUND;
//        }

        if (enemies != null) {
            this.enemies.addAll(enemies);
        }
    }

    public RoomOutCome enter(Scanner input) {
        while (true) {
            this.showRoomInfo();

            if (this.player.getCompletedRooms().contains(this.roomType)) {
                System.out.println("\n" + "You have already cleared this room.");
                System.out.println("\n" + "Move to another room...");
                Utility.enterToContinue();
                return new RoomOutCome(RoomResult.COMPLETED, RoomType.TWO);
            }

            System.out.println("1. Investigate 'X' marking " + (this.allEnemiesKilled() ? "(cleared)" : ""));

            int choice = this.userInput();

            switch (choice) {
                case 0 -> {
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
                        if (this.getCombat().startCombat(this.getPlayer(), this.getEnemies().getFirst())) {
                            this.getEnemies().removeFirst();
                        } else {
                            return new RoomOutCome(RoomResult.DEATH, null);
                        }
                    } else {
                        System.out.println("\n" + "Nothing left here.");
                        Utility.enterToContinue();
                    }
                }
                case 2 -> { // Treasure
                    this.treasureFound();
                }
                case 3 -> {
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
        System.out.println("\n" + this.display.getMap());
    }

    private Player getPlayer() {
        return this.player;
    }

    private CombatSystem getCombat() {
        return this.combat;
    }

    private boolean allEnemiesKilled() {
        return this.enemies.isEmpty();
    }

    private ArrayList<Enemy> getEnemies() {
        return this.enemies;
    }

    private boolean isTreasureFound() {
        return this.treasureState == TreasureStatus.FOUND;
    }

    private void treasureFound() {
        if (this.isTreasureFound()) {
            System.out.println("\n" + "You already found this treasure.");
            Utility.enterToContinue();
        } else {
            this.treasureState = TreasureStatus.FOUND;
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
        //System.out.println("\n");
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

    private Item getTreasure() {
        return this.itemDatabase.getRandomItem();
    }

    private int userInput() {
        int maxChoice = 1;
        if (this.treasureState.equals(TreasureStatus.AVAILABLE)
                || this.treasureState.equals(TreasureStatus.FOUND)) {
            maxChoice = 2;
            System.out.println("2. Investigate '?' marking " + (this.isTreasureFound() ? "(found)" : ""));

        }
        if (this.allEnemiesKilled()) {
            maxChoice = 3;
            System.out.println("3. Proceed to next room");
            System.out.println("0. Back to menu");
            return handleDecision(0, maxChoice);
        } else {
            System.out.println("0. Back to menu");
            return handleDecision(0, maxChoice);
        }
    }
}
