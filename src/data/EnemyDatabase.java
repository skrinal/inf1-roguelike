package data;

import model.Enemy;
import model.enemies.SkeletonWarrior;
import model.enums.room.RoomType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EnemyDatabase {
    private Map<RoomType, ArrayList<Enemy>> enemies;

    public EnemyDatabase() {
        this.enemies = new HashMap<>();

        this.loadEnemies();
    }

    private void loadEnemies() {
        ArrayList<Enemy> roomOneEnemies = new ArrayList<>();
        roomOneEnemies.add(new SkeletonWarrior("Maximus", 100));
        roomOneEnemies.add(new SkeletonWarrior("Bonecrusher", 120));

        this.enemies.put(RoomType.ONE, roomOneEnemies);
    }

    public ArrayList<Enemy> getEnemies(RoomType roomType) {
        return new ArrayList<>(this.enemies.getOrDefault(roomType, new ArrayList<>()));
    }

}
