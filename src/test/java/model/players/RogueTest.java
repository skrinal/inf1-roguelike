package model.players;

import model.Enemy;
import model.enemies.DemonLord;
import model.enums.ClassPower;
import model.enums.CombatTag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import output.SilentOutput;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RogueTest {

    Rogue roguePlayer;
    Rogue roguePlayerWithLevel;

    Enemy enemy;

    @BeforeEach
    void setUp() {
        roguePlayer = new Rogue("Rogues");
        roguePlayerWithLevel = new Rogue("RoguesLevelos", 50);

        roguePlayer.setSystemOutput(new SilentOutput());
        roguePlayerWithLevel.setSystemOutput(new SilentOutput());

        enemy = new DemonLord("Bengos", 5);
        enemy.setSystemOutput(new SilentOutput());
    }

    @Test
    void testRogueLevelInitialization() {
        assertNotNull(roguePlayerWithLevel);
        assertEquals(50, roguePlayerWithLevel.getLevel());
    }

    @Test
    void testRogueGetters() {
        assertEquals(ClassPower.ENERGY.toString(), roguePlayer.getPowerString());
        assertEquals(CombatTag.ROGUE, roguePlayer.getCombatTag());
        assertEquals("Rogues", roguePlayer.getName());
        assertEquals(null, roguePlayer.getEquippedArmor());
        assertEquals(null, roguePlayer.getEquippedWeapon());

        assertEquals(100, roguePlayer.getMaxHp());
        assertEquals(8, roguePlayer.getTotalAttack());
        assertEquals(5, roguePlayer.getTotalDefense());
        assertEquals(150, roguePlayer.getMaxPower());
    }

    @Test
    void testRogueBeforeTurn() {
        roguePlayer.performeBasicAbility(enemy);

        roguePlayer.beforeTurn();

        assertEquals(140, roguePlayer.getPower());
    }

    @Test
    void testRogueAfterTurnVanish() {
        roguePlayer.performeSpecialAbility(enemy);

        roguePlayer.beforeTurn();

        assertEquals(90, roguePlayer.getPower());
    }


}
