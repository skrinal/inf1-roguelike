package model.players;

import model.Enemy;
import model.Player;
import model.enemies.DemonLord;
import model.enums.ClassPower;
import model.enums.PlayerClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MageTest {

    Player magePlayer;
    Enemy enemy;

    @BeforeEach
    void setUp() {
        magePlayer = new Mage("Gandalfos");
        enemy = new DemonLord("Bengoro", 5);
    }

    @Test
    void testMageGetters() {
        assertNotNull(magePlayer);
        assertInstanceOf(Mage.class, magePlayer);

        assertTrue(magePlayer.getPower() > 0);

        assertEquals("Gandalfos", magePlayer.getName());
        assertEquals(100, magePlayer.getMaxPower());
        assertEquals(ClassPower.MANA.toString(), magePlayer.getPowerString());
        assertEquals(PlayerClass.MAGE, magePlayer.getClassType());
    }

    @Test
    void testMageBasicAbility() {
        magePlayer.performeBasicAbility(enemy);

        assertEquals("Frostbolt", magePlayer.getBasicAbilityName());
        assertEquals(10, magePlayer.getBasicAbilityCost());

        assertEquals(90, magePlayer.getPower());
        assertEquals(10, magePlayer.getBasicAbilityCost());
        assertNotEquals(50, enemy.getHp());
    }

    @Test
    void testMageSpecialAbility() {
        magePlayer.performeSpecialAbility(enemy);

        assertEquals("FireBlast", magePlayer.getSpecialAbilityName());
        assertEquals(50, magePlayer.getSpecialAbilityCost());

        assertEquals(50, magePlayer.getPower());
        assertEquals(50, magePlayer.getBasicAbilityCost());

        assertNotEquals(50, enemy.getHp());
    }

    @Test
    void testMageUtilityAbility() {
        magePlayer.performeUtilityAbility();

        assertEquals("Cloak of Shadows", magePlayer.getUtilityAbilityName());
        assertEquals(15, magePlayer.getUtilityAbilityCost());

        assertEquals(75, magePlayer.getPower());
        assertEquals(15, magePlayer.getUtilityAbilityCost());

    }
}
