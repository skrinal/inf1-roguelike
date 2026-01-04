package data;

import model.Enemy;
import model.enemies.DemonLord;
import model.enemies.Dragon;
import model.enemies.Elf;
import model.enemies.Troll;
import model.enemies.Skeleton;
import model.enums.room.RoomType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EnemyDatabase {
    private final Map<RoomType, ArrayList<Enemy>> enemies;
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
        //TODO: change Names
        ArrayList<Enemy> roomOneEnemies = new ArrayList<>();
        roomOneEnemies.add(new Skeleton("Maximus"));

        ArrayList<Enemy> roomTwoEnemies = new ArrayList<>();
        roomTwoEnemies.add(new Skeleton("Rotting Guard"));
        roomTwoEnemies.add(new Skeleton("Bonecrusher"));
        roomTwoEnemies.add(new Troll("Cave Troll"));

        ArrayList<Enemy> roomThreeEnemies = new ArrayList<>();
        roomTwoEnemies.add(new Elf("Elite Guard"));


        ArrayList<Enemy> roomFourEnemies = new ArrayList<>();
        roomTwoEnemies.add(new Dragon("Gatekeeper"));


        ArrayList<Enemy> roomFiveEnemies = new ArrayList<>();
        roomTwoEnemies.add(new DemonLord("The Ashen Warden"));


        this.enemies.put(RoomType.ONE, roomOneEnemies);
        this.enemies.put(RoomType.TWO, roomTwoEnemies);
        this.enemies.put(RoomType.THREE, roomThreeEnemies);
        this.enemies.put(RoomType.FOUR, roomFourEnemies);
        this.enemies.put(RoomType.FIVE, roomFiveEnemies);
    }

    public ArrayList<Enemy> getEnemies(RoomType roomType) {
        return new ArrayList<>(this.enemies.getOrDefault(roomType, new ArrayList<>()));
    }

}
