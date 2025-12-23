package data;

import model.Enemy;
import model.enemies.Skeleton;
import model.enums.room.RoomType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EnemyDatabase {
    private Map<RoomType, ArrayList<Enemy>> enemies;
    private static EnemyDatabase instance;

    public static EnemyDatabase getInstance() {
        if (instance == null) {
            instance = new EnemyDatabase();
        }
        return instance;
    }

    private EnemyDatabase() {
        this.enemies = new HashMap<>();

        this.loadEnemies();
    }

    private void loadEnemies() {
        ArrayList<Enemy> roomOneEnemies = new ArrayList<>();
        roomOneEnemies.add(new Skeleton("Maximus"));
        roomOneEnemies.add(new Skeleton("Bonecrusher"));

        ArrayList<Enemy> roomTwoEnemies = new ArrayList<>();
        roomTwoEnemies.add(new Skeleton("Maximus"));
        roomTwoEnemies.add(new Skeleton("Bonecrusher"));

        this.enemies.put(RoomType.ONE, roomOneEnemies);
        this.enemies.put(RoomType.TWO, roomTwoEnemies);
    }

    public ArrayList<Enemy> getEnemies(RoomType roomType) {
        return new ArrayList<>(this.enemies.getOrDefault(roomType, new ArrayList<>()));
    }

}
