package model.enemies;

import model.enums.CombatTag;
import model.enums.type.EnemyType;
import model.interfaces.Boss;
import model.interfaces.SpectralAttacker;
import model.players.Rogue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import output.SilentOutput;

import static org.junit.jupiter.api.Assertions.*;


class DemonLordTest {

    DemonLord demonLord;
    DemonLord demonLordLevel;

    Rogue rogue;

    @BeforeEach
    void setUp() {
        demonLord = new DemonLord("Demonlordos");
        demonLordLevel = new DemonLord("DemonlordosLevelos", 50);

        rogue = new Rogue("Rogues");

        demonLord.setSystemOutput(new SilentOutput());
        demonLordLevel.setSystemOutput(new SilentOutput());
        rogue.setSystemOutput(new SilentOutput());
    }

    @Test
    void testDemonLordLevelInitialization() {
        assertNotNull(demonLord);
        assertEquals(1, demonLord.getLevel());

        assertNotNull(demonLordLevel);
        assertEquals(50, demonLordLevel.getLevel());
    }

    @Test
    void testDemonLordInstance() {
        assertInstanceOf(Boss.class, demonLord);
        assertInstanceOf(SpectralAttacker.class, demonLord);
    }

    @Test
    void testDemonLordGetters() {
        assertEquals(CombatTag.DEMON, demonLord.getCombatTag());
        assertEquals(EnemyType.BOSS, demonLord.getEnemyType());
    }

    @Test
    void testDemonLordBasicAbility() {
        demonLord.performeBasicAbility(rogue);

        assertNotEquals(rogue.getMaxHp(), rogue.getHp());
    }

    /*
    Can't test as I don't know the rolled number.
    (Unless I mock the random generator and use ChatGPT :D)

    @Test
    void testDemonLordSpecialAbility() {

    }
*/

    @Test
    void testDemonLordSpectralDamage() {
        demonLord.performSpectralDamage(rogue);

        assertEquals(rogue.getMaxHp(), rogue.getHp());
    }
}
