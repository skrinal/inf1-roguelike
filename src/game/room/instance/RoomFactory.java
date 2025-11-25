package game.room.instance;

import data.EnemyDatabase;
import game.room.combat.CombatSystem;
import model.Enemy;
import model.Player;
import model.enums.room.RoomMap;
import model.enums.room.RoomType;

import java.util.ArrayList;

public class RoomFactory {
    public RoomFactory() {

    }
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
