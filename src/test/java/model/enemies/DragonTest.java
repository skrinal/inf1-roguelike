package model.enemies;

import model.Player;
import model.enums.CombatTag;
import model.enums.type.EnemyType;
import model.players.Rogue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DragonTest {

    Player player;
    Dragon dragon;

    @BeforeEach
    void setUp() {
        player = new Rogue("Rogues", 10);
        dragon = new Dragon("Dragos", 5);
    }

    @Test
    void testDragonGetters() {
        assertEquals("Dragos", dragon.getName());
        assertEquals(5, dragon.getLevel());
        assertEquals(EnemyType.ELITE, dragon.getEnemyType());
        assertEquals(CombatTag.DRAGON, dragon.getCombatTag());
    }

    //TODO: FINIS TEST WHEN DRAGON IS DONE
}