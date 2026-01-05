package game.room.combat;

import data.Items;
import model.Enemy;
import model.Player;
import model.enemies.Skeleton;
import model.players.Warrior;
import output.SilentOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CombatSystemTest {
    private Player player;
    private Enemy enemy;

    @BeforeEach
    void setUp() {
        player = new Warrior("TestPlayer");
        enemy = new Skeleton("TestEnemy");
        player.setSystemOutput(new SilentOutput());
        enemy.setSystemOutput(new SilentOutput());
    }

    @Test
    void testPotionRemovalAfterUse() {
        Items potionEnum = Items.HEALTH_POTION;
        player.addItem(potionEnum.getItem());
        
        assertTrue(player.getInventory().containsKey(potionEnum.getItem()));
        assertEquals(1, player.getInventory().get(potionEnum.getItem()));
        
        player.useOrEquip(potionEnum.getItem());
        
        assertFalse(player.getInventory().containsKey(potionEnum.getItem()), "Potion should be removed from inventory after use");
    }
}
