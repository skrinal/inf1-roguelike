package model.players;

import model.Enemy;
import model.enemies.DemonLord;
import model.enums.ClassPower;
import model.enums.CombatTag;
import model.enums.status.StatusEffects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import output.SilentOutput;

import static org.junit.jupiter.api.Assertions.*;

class MageTest {

    Mage magePlayer;
    Mage magePlayerWithLevel;

    Enemy enemy;

    @BeforeEach
    void setUp() {
        magePlayer = new Mage("Gandalfos");
        magePlayerWithLevel = new Mage("GandalfosLevelos", 50);

        magePlayer.setSystemOutput(new SilentOutput());
        magePlayerWithLevel.setSystemOutput(new SilentOutput());

        enemy = new DemonLord("Bengoro", 5);
        enemy.setSystemOutput(new SilentOutput());
    }

    @Test
    void testMageLevelInitialization() {
        assertNotNull(magePlayerWithLevel);
        assertEquals(50, magePlayerWithLevel.getLevel());
    }

    @Test
    void testMageGetters() {
        assertEquals(ClassPower.MANA.toString(), magePlayer.getPowerString());
        assertEquals(CombatTag.MAGE, magePlayer.getCombatTag());
        assertEquals("Gandalfos", magePlayer.getName());
        assertNull(magePlayer.getEquippedArmor());
        assertNull(magePlayer.getEquippedWeapon());

        assertEquals(80, magePlayer.getMaxHp());
        assertEquals(10, magePlayer.getTotalAttack());
        assertEquals(3, magePlayer.getTotalDefense());
        assertEquals(100, magePlayer.getMaxPower());
    }

    @Test
    void testMageBeforeTurn() {
        magePlayer.performSpecialAbility(enemy);

        magePlayer.beforeTurn();

        assertEquals(65, magePlayer.getPower());
    }

    @Test
    void testMageAfterTurnInvisible() {
        magePlayer.performeUtilityAbility();

        magePlayer.beforeTurn();

        assertEquals(90, magePlayer.getPower());
    }

    @Test
    void testMageBasicAbility() {
        magePlayer.performBasicAbility(enemy);

        assertEquals("Frostbolt", magePlayer.getBasicAbilityName());
        assertEquals(10, magePlayer.getBasicAbilityCost());

        assertEquals(90, magePlayer.getPower());
        assertEquals(10, magePlayer.getBasicAbilityCost());
        assertNotEquals(enemy.getMaxHp(), enemy.getHp());
    }

    @Test
    void testMageSpecialAbility() {
        magePlayer.performSpecialAbility(enemy);

        assertEquals("Fireblast", magePlayer.getSpecialAbilityName());
        assertEquals(50, magePlayer.getSpecialAbilityCost());

        assertEquals(50, magePlayer.getPower());
        assertEquals(10, magePlayer.getBasicAbilityCost());

        assertNotEquals(enemy.getMaxHp(), enemy.getHp());
    }

    @Test
    void testMageUtilityAbility() {
        magePlayer.performeUtilityAbility();

        assertEquals("Cloak of Shadows", magePlayer.getUtilityAbilityName());
        assertEquals(15, magePlayer.getUtilityAbilityCost());

        assertEquals(85, magePlayer.getPower());
        assertEquals(15, magePlayer.getUtilityAbilityCost());
        assertEquals(StatusEffects.INVISIBILITY, magePlayer.getStatusEffects().keySet().toArray()[0]);
    }
}
