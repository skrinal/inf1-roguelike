package model.enemies;

import model.Player;
import model.enums.CombatTag;
import model.enums.status.StatusEffects;
import model.enums.type.EnemyType;
import model.players.Rogue;
import output.SilentOutput;
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

        dragon.setSystemOutput(new SilentOutput());
        player.setSystemOutput(new SilentOutput());
    }

    @Test
    void testDragonGetters() {
        assertEquals("Dragos", dragon.getName());
        assertEquals(5, dragon.getLevel());
        assertEquals(EnemyType.ELITE, dragon.getEnemyType());
        assertEquals(CombatTag.DRAGON, dragon.getCombatTag());
    }

    @Test
    void testDragonCombatAbilities() {
        int initialHp = player.getHp();
        dragon.performBasicAbility(player);
        assertTrue(player.getHp() < initialHp, "Player should have taken damage from basic ability");

        // Dragon Special Ability Cycle
        // Turn 1: Roar + Buff
        dragon.performSpecialAbility(player);
        assertTrue(dragon.hasStatusEffect(StatusEffects.DRAGON_MIGHT), "Dragon should have DRAGON_MIGHT buff");

        // Turn 2: Charge 1
        dragon.performSpecialAbility(player);
        // Turn 3: Charge 2
        dragon.performSpecialAbility(player);

        // Turn 4: Fire Breath
        initialHp = player.getHp();
        dragon.performSpecialAbility(player);
        assertTrue(player.getHp() < initialHp, "Player should have taken massive damage from Fire Breath");
        assertTrue(player.hasStatusEffect(StatusEffects.BURN), "Player should have BURN effect");
    }

    @Test
    void testDragonSpectralAbility() {
        assertTrue(dragon.getEnemyType().isSpectral());
        int initialHp = player.getHp();
        dragon.performSpectralDamage(player);

        assertTrue(player.getHp() < initialHp, "Player should have taken spectral damage");
    }

    @Test
    void testDragonOnBossTurnSequence() {
        int initialHp = player.getHp();

        // Turn 1: Roar + Basic Attack
        dragon.onBossTurn(player);
        assertTrue(dragon.hasStatusEffect(StatusEffects.DRAGON_MIGHT));
        assertTrue(player.getHp() < initialHp, "Player should have taken damage from Roar+Basic turn");

        // Simulating until fire breath happens
        boolean fireBreathHappened = false;
        for (int i = 0; i < 20; i++) {
            dragon.onBossTurn(player);
            if (player.hasStatusEffect(StatusEffects.BURN)) {
                fireBreathHappened = true;
                break;
            }
        }
        assertTrue(fireBreathHappened, "Fire breath should eventually happen via onBossTurn");
    }

    @Test
    void testDragonOnBossTurnSpectral() {
        player.applyStatusEffect(StatusEffects.INVISIBILITY, 5);
        int initialHp = player.getHp();
        dragon.onBossTurn(player);
        assertTrue(player.getHp() < initialHp, "Should have taken spectral damage while invisible");
    }
}