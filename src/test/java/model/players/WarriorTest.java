package model.players;

import model.Enemy;
import model.enums.ClassPower;
import model.enums.CombatTag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WarriorTest {

    Warrior warriorPlayer;
    Warrior warriorPlayerWithLevel;

    @BeforeEach
    void setUp() {
        warriorPlayer = new Warrior("Warriors");
        warriorPlayerWithLevel = new Warrior("WarriorsLevelos", 50);
    }

    @Test
    void testWarriorLevelInitialization() {
        assertNotNull(warriorPlayerWithLevel);
        assertEquals(50, warriorPlayerWithLevel.getLevel());
    }

    @Test
    void testWarriorGetters() {
        assertEquals(ClassPower.RAGE.toString(), warriorPlayer.getPowerString());
        assertEquals(CombatTag.WARRIOR, warriorPlayer.getCombatTag());

        assertEquals("Warriors", warriorPlayer.getName());
    }

    @Test
    void testAbilities(){
        assertEquals(20, warriorPlayer.getBasicAbilityCost());
        assertEquals(30, warriorPlayer.getSpecialAbilityCost());
        assertEquals(10, warriorPlayer.getUtilityAbilityCost());

        assertEquals("Bloodthirst", warriorPlayer.getBasicAbilityName());
        assertEquals("Execute", warriorPlayer.getSpecialAbilityName());
        assertEquals("War Stance", warriorPlayer.getUtilityAbilityName());
    }
}
