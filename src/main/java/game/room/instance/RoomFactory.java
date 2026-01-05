package game.room.instance;

import data.EnemyDatabase;
import game.room.combat.CombatSystem;
import model.Enemy;
import model.Player;
import model.enums.room.RoomMap;
import model.enums.room.RoomType;

import java.util.ArrayList;

/**
 * The class is responsible for creating instances of Room
 * based on specified room types.
 */
public class RoomFactory {

    /**
     * Constructs a new instance of the RoomFactory class.
     * This constructor initializes a factory for creating Room objects
     * based on specific room types, players, and associated attributes.
     * Needed for possible other types of rooms in the future.
     */
    public RoomFactory() {
        // x
    }

    /**
     * Creates a new Room instance based on the specified room type, player,
     * and combat system. The method retrieves the relevant list of enemies
     * and other attributes required to initialize the room.
     *
     * @param roomType the type of the room to be created
     * @param player the player who will interact with the room
     * @param combat the combat system to be used within the room
     * @return the newly created Room instance, or null if the room type
     *         is not recognized
     */
    public Room createRoom(RoomType roomType, Player player, CombatSystem combat) {
        EnemyDatabase db = EnemyDatabase.getInstance();

        switch (roomType) {
            case ONE -> {
                ArrayList<Enemy> enemies = db.getEnemies(RoomType.ONE);
                return new Room(roomType, player, combat, RoomMap.ROOM_ONE, enemies, true);
            }
            case TWO -> {
                ArrayList<Enemy> enemies = db.getEnemies(RoomType.TWO);
                return new Room(roomType, player, combat, RoomMap.ROOM_TWO, enemies, false);
            }
            case THREE -> {
                ArrayList<Enemy> enemies = db.getEnemies(RoomType.THREE);
                return new Room(roomType, player, combat, RoomMap.ROOM_TREE, enemies, true);
            }
            case FOUR -> {
                ArrayList<Enemy> enemies = db.getEnemies(RoomType.FOUR);
                return new Room(roomType, player, combat, RoomMap.ROOM_FOUR, enemies, true);
            }
            case FIVE -> {
                ArrayList<Enemy> enemies = db.getEnemies(RoomType.FIVE);
                return new Room(roomType, player, combat, RoomMap.ROOM_FIVE, enemies, false);
            }
        }
        return null;
    }
}
