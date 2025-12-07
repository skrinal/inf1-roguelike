// java
package data;

import model.enemies.Skeleton;
import model.enums.room.RoomType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EnemyDatabaseTest {

    private final EnemyDatabase db = EnemyDatabase.getInstance();

    @Test
    void getEnemiesForFirstRoom() {
        List<?> enemies = this.db.getEnemies(RoomType.ONE);

        assertEquals(2, enemies.size(), "Room ONE should have 2 enemies configured");
        assertInstanceOf(Skeleton.class, enemies.get(0), "First enemy should be a Skeleton");
        assertInstanceOf(Skeleton.class, enemies.get(1), "Second enemy should be a Skeleton");
    }

    @Test
    void getEnemiesForNUllRoom() {
        List<?> enemies = this.db.getEnemies(null);

        assertNotNull(enemies, "Returned list should not be null");
        assertTrue(enemies.isEmpty(), "Unconfigured room should return an empty list");
    }
}
