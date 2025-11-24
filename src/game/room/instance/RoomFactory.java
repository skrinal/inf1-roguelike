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
                return new Room(player, combat, RoomMap.ROOM_ONE, enemies, true);
            }
            case TWO -> {
                ArrayList<Enemy> enemies = db.getEnemies(RoomType.TWO);
                return new Room(player, combat, RoomMap.ROOM_TWO, enemies, false);
            }
        }
        return null;
    }
}
