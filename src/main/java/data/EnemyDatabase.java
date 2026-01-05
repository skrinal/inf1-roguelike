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

/**
 * EnemyDatabase is a singleton class responsible for managing and storing enemies for
 * different room types in the game.
 * Possible change to normal class in the future.
 * If adding multiple players, this will be a problem.
 */
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
        // Scaling enemies for progression: Easy earlier, challenging boss.
        ArrayList<Enemy> roomOneEnemies = new ArrayList<>();
        roomOneEnemies.add(new Skeleton("Maximus", 1));

        ArrayList<Enemy> roomTwoEnemies = new ArrayList<>();
        roomTwoEnemies.add(new Skeleton("Rotting Guard", 2));
        roomTwoEnemies.add(new Skeleton("Bonecrusher", 2));
        roomTwoEnemies.add(new Troll("Cave Troll", 5));

        ArrayList<Enemy> roomThreeEnemies = new ArrayList<>();
        roomThreeEnemies.add(new Elf("Elite Guard", 6));


        ArrayList<Enemy> roomFourEnemies = new ArrayList<>();
        roomFourEnemies.add(new Dragon("Gatekeeper", 7));


        ArrayList<Enemy> roomFiveEnemies = new ArrayList<>();
        roomFiveEnemies.add(new DemonLord("The Ashen Warden", 9));


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
