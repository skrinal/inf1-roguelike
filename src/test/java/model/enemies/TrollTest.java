package model.enemies;

import model.Player;
import model.enums.CombatTag;
import model.enums.type.EnemyType;
import model.players.Rogue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import output.SilentOutput;

import static org.junit.jupiter.api.Assertions.*;

class TrollTest {

    Player player;
    Troll troll;
    Troll trollWithLevel;

    @BeforeEach
    void setUp() {
        player = new Rogue("Rogues", 5);
        troll = new Troll("Trolls", 5);
        trollWithLevel = new Troll("TrollsLevelos", 50);

        player.setSystemOutput(new SilentOutput());
        troll.setSystemOutput(new SilentOutput());
        trollWithLevel.setSystemOutput(new SilentOutput());
    }

    @Test
    void testRogueLevelInitialization() {
        assertNotNull(trollWithLevel);
        assertEquals(50, trollWithLevel.getLevel());
    }

    @Test
    void testTrollGeters() {
        assertEquals("Trolls", troll.getName());
        assertEquals(5, troll.getLevel());
        assertEquals(EnemyType.TRASH, troll.getEnemyType());
        assertEquals(CombatTag.TROLL, troll.getCombatTag());
    }

    @Test
    void testTrollBasicAbility() {
        troll.performeBasicAbility(player);

        assertNotEquals(player.getMaxHp(), player.getHp());
    }

    @Test
    void testTrollSpecialAbility() {
        troll.performeSpecialAbility(player);

        assertNotEquals(player.getMaxHp(), player.getHp());
    }


}