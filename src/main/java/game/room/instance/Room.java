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
import output.ConsoleOutput;

import java.util.ArrayList;
import java.util.List;

import static utility.Utility.handleDecision;

/**
 * Represents a game room with specified attributes and functionalities.
 * The Room class manages what happens in a room, including enemies and treasure.
 */
public class Room {
    private final RoomType roomType;
    private final Player player;
    private boolean playerHasTreasure;
    private final CombatSystem combat;
    private final RoomMap display;
    private final ArrayList<Enemy> enemies;
    private final ItemDatabase itemDatabase;
    private final boolean isTreasurePresent;
    private ConsoleOutput out = new ConsoleOutput();

    /**
     * Constructs a Room object with the specified parameters.
     *
     * @param roomType the type of the room
     * @param player the player entering the room
     * @param combat the combat system used in the room
     * @param display the map display of the room
     * @param enemies the list of enemies present in the room (can be null)
     * @param treasurePresent indicates if treasure is present in the room
     */
    public Room(RoomType roomType, Player player, CombatSystem combat, RoomMap display, List<Enemy> enemies, boolean treasurePresent) {
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

    /**
     * Sets the output stream for the room.
     */
    public void setOut(ConsoleOutput out) {
        this.out = out;
    }

    /**
     * Handles the room interaction.
     * Output of the description and map of the room with available options.
     * Checks if the player has already cleared the room (Enemies and Treasure),
     * if yes, he is redirected to the next room.
     * If no, then he needs to clear the room.
     */
    public RoomOutCome enter() {
        while (true) {
            this.showRoomInfo();

            if (this.player.getCompletedRooms().contains(this.roomType) && this.playerHasTreasure) {
                return this.nextRoom();
            }

            this.out.println("1. Investigate 'X' marking " + (this.allEnemiesKilled() ? "(cleared)" : ""));

            int choice = this.userInput();

            switch (choice) {
                case 0 -> {
                    return this.backToMenu();
                }
                case 1 -> {
                    RoomOutCome result = this.fightEnemy();
                    if (result != null) {
                        return result;
                    }
                }
                case 2 -> this.treasureFound();
                case 3 -> {
                    return this.nextRoom(this.roomType);
                }
                default -> {
                    this.out.println("Invalid selection. Try again");
                    this.out.pause();
                }
            }
        }
    }

    private RoomOutCome nextRoom() {
        this.out.println("\n" + "You have already cleared this room.");
        this.out.println("\n" + "Move to another room...");
        this.out.pause();
        return new RoomOutCome(RoomResult.COMPLETED, RoomType.TWO);
    }

    private RoomOutCome backToMenu() {
        if (this.allEnemiesKilled() && this.isTreasureFound()) {
            if (this.player.getCompletedRooms().contains(this.roomType)) {
                return new RoomOutCome(RoomResult.EXIT, null); // back to menu
            }
            this.player.addCompletedRoom(this.roomType);
        }
        return new RoomOutCome(RoomResult.EXIT, null);
    }

    private RoomOutCome fightEnemy() {
        if (this.allEnemiesKilled()) {
            this.out.println("\n" + "All enemies has been defeated.");
            this.out.pause();
            return null;
        }

        if (this.combat.startCombat(this.player, this.enemies.getFirst())) {
            this.enemies.removeFirst();
            return null;
        } else {
            return new RoomOutCome(RoomResult.DEATH, null);
        }
    }

    private RoomOutCome nextRoom(RoomType roomType) {
        this.player.addCompletedRoom(this.roomType);
        return new RoomOutCome(RoomResult.COMPLETED, roomType.next());
    }

    private void showRoomInfo() {
        this.out.println("\n" + this.display.getDescription());
        this.out.println("╔═════════════════════╗");
        this.out.println("║ You are in room " + this.roomType.name() + " ║");

        this.out.println(this.display.getMap());
    }

    private boolean allEnemiesKilled() {
        return this.enemies.isEmpty();
    }

    private boolean isTreasureFound() {
        return this.playerHasTreasure;
    }

    private void treasureFound() {
        if (this.isTreasureFound()) {
            this.out.println("\n" + "You already found this treasure.");
            this.out.pause();
        } else {
            this.playerHasTreasure = this.player.addTreasureFound(this.roomType);

            Item item = this.getTreasure();
            if (item == null) {

                return;
            }
            this.player.addItem(item);
            this.itemOutput(item);

            this.out.pause();
        }
    }

    private Item getTreasure() {
        return this.itemDatabase.getRandomItem();
    }

    private void itemOptions(Item item) {
        switch (item.type()) {
            case WEAPON, ARMOR -> {
                this.out.println("1. Equip");
                this.out.println("2. Keep");
            }
            case POTION -> {
                this.out.println("1. Use");
                this.out.println("2. Keep");
            }
            default -> System.out.println("Invalid selection. Try again");
        }

        switch (handleDecision(1, 2)) {
            case 1 -> {
                switch (item.type()) {
                    case WEAPON -> {
                        this.player.setEquippedWeapon(item);
                        this.out.println("\n" + "Item has been equipped.");
                    }
                    case ARMOR -> {
                        this.player.setEquippedArmor(item);
                        this.out.println("\n" + "Item has been equipped.");
                    }
                    case POTION -> {
                        this.player.restorePower(item.value());
                        this.out.println("\n" + "Potion has been used.");
                    }
                    default -> System.out.println("Invalid selection. Try again");
                }
            }
            case 2 -> this.out.println("Item has been kept.");
            default -> System.out.println("Invalid selection. Try again");
        }
    }

    private void itemOutput(Item item) {
        this.out.println("\n" + "You have found");
        item.displayInfo();
        this.itemOptions(item);
    }

    private int userInput() {
        int maxChoice = 1;
        if (this.isTreasurePresent) {
            maxChoice = 2;
            this.out.println("2. Investigate '?' marking " + (this.isTreasureFound() ? "(found)" : ""));
        }

        if (this.allEnemiesKilled()) {
            maxChoice = 3;
            this.out.println("3. Proceed to next room");
            this.out.println("0. Back to menu");
            return handleDecision(0, maxChoice);
        }

        this.out.println("0. Back to menu");
        return handleDecision(0, maxChoice);
    }
}
