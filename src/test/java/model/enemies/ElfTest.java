package model.enemies;

import model.Player;
import model.enums.CombatTag;
import model.enums.status.StatusEffects;
import model.enums.type.EnemyType;
import model.players.Rogue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import output.SilentOutput;

import static org.junit.jupiter.api.Assertions.*;

class ElfTest {

    Player player;
    Elf elf;
    Elf elfLevel;

    @BeforeEach
    void setUp() {
        player = new Rogue("Roguos", 10);
        elf = new Elf("Elves", 5);
        elfLevel = new Elf("ElvesLevelos", 50);

        player.setSystemOutput(new SilentOutput());
        elf.setSystemOutput(new SilentOutput());
        elfLevel.setSystemOutput(new SilentOutput());
    }

    @Test
    void testElfLevelInitialization() {
        assertNotNull(elfLevel);
        assertEquals(50, elfLevel.getLevel());
    }

    @Test
    void testElfGetters() {
        assertEquals("Elves", elf.getName());
        assertEquals(5, elf.getLevel());
        assertEquals(CombatTag.ELF, elf.getCombatTag());
        assertEquals(EnemyType.ELITE, elf.getEnemyType());
    }



    @Test
    void testElfBasicAbility() {
        elf.performeBasicAbility(player);

        assertNotEquals(player.getMaxHp(), player.getHp());
    }

    @Test
    void testElfSpecialAbility() {
        elf.performeSpecialAbility(player);

        assertTrue(elf.getStatusEffects().containsKey(StatusEffects.ELF_STRENGTH));
    }

    @Test
    void testElfSpectralDamage() {
        elf.performSpectralDamage(player);

        assertEquals(player.getMaxHp(), player.getHp());
    }

}