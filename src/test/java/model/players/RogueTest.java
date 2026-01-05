package model.players;

import model.Enemy;
import model.enemies.DemonLord;
import model.enums.ClassPower;
import model.enums.CombatTag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import output.SilentOutput;

import static org.junit.jupiter.api.Assertions.*;

class RogueTest {

    Rogue rogue;
    Rogue rogueLevel;

    Enemy enemy;

    @BeforeEach
    void setUp() {
        rogue = new Rogue("Rogues");
        rogueLevel = new Rogue("RoguesLevelos", 50);

        rogue.setSystemOutput(new SilentOutput());
        rogueLevel.setSystemOutput(new SilentOutput());

        enemy = new DemonLord("Bengos", 5);
        enemy.setSystemOutput(new SilentOutput());
    }

    @Test
    void testRogueLevelInitialization() {
        assertNotNull(rogueLevel);
        assertEquals(50, rogueLevel.getLevel());

        assertNotNull(rogue);
        assertEquals(1, rogue.getLevel());
    }

    @Test
    void testRogueGetters() {
        assertEquals(ClassPower.ENERGY.toString(), rogue.getPowerString());
        assertEquals(CombatTag.ROGUE, rogue.getCombatTag());
        assertEquals("Rogues", rogue.getName());
        assertNull(rogue.getEquippedArmor());
        assertNull(rogue.getEquippedWeapon());

        assertEquals(100, rogue.getMaxHp());
        assertEquals(8, rogue.getTotalAttack());
        assertEquals(5, rogue.getTotalDefense());
        assertEquals(150, rogue.getMaxPower());
    }

    @Test
    void testRogueBeforeTurn() {
        rogue.performBasicAbility(enemy);

        rogue.beforeTurn();

        assertEquals(140, rogue.getPower());
    }

    @Test
    void testRogueAfterTurnVanish() {
        rogue.performSpecialAbility(enemy);

        assertEquals(90, rogue.getPower());

        rogue.beforeTurn();
    }


}
