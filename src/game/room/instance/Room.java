package game.room.instance;


import data.ItemDatabase;
import game.room.RoomOutCome;
import game.room.combat.CombatSystem;
import model.Enemy;
import model.Item;
import model.Player;
import model.enums.room.RoomMap;
import utility.Utility;

import java.util.ArrayList;
import java.util.Scanner;

import static utility.Utility.handleDecision;

public abstract class Room {
    private Player player;
    private CombatSystem combat;
    private RoomMap display;
    private ArrayList<Enemy> enemies;
    private ItemDatabase itemDatabase;

    private boolean treasureFound;

    public Room(Player player, CombatSystem combat, RoomMap display, ArrayList<Enemy> enemies) {
        this.player = player;
        this.combat = combat;
        this.display = display;
        this.enemies = new ArrayList<>();
        this.itemDatabase = ItemDatabase.getInstance(player);

        this.treasureFound = false;

        if (enemies != null) {
            this.enemies.addAll(enemies);
        }
    }

    public abstract RoomOutCome enter(Scanner input);

    protected void showRoomInfo() {
        System.out.println("\n" + this.display.getDescription());
        System.out.println("\n" + this.display.getMap());
    }

    protected Player getPlayer() {
        return this.player;
    }

    protected CombatSystem getCombat() {
        return this.combat;
    }

    protected boolean allEnemiesKilled() {
        return this.enemies.isEmpty();
    }

    protected ArrayList<Enemy> getEnemies() {
        return this.enemies;
    }

    protected boolean isTreasureFound() {
        return this.treasureFound;
    }

    protected void treasureFound() {
        if (this.treasureFound) {
            System.out.println("You already found the treasure.");

            Utility.enterToContinue();
        } else {
            this.treasureFound = true;
            Item item = this.getTreasure();
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

    protected int userInput() {
        if (this.allEnemiesKilled()) {
            System.out.println("3. Proceed to next room");
            return handleDecision(0, 3);
        } else {
            return handleDecision(0, 2);
        }
    }
}
