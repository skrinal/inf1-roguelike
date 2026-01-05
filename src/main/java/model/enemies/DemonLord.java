package model.enemies;

import data.Items;
import model.Character;
import model.Enemy;
import model.Player;
import model.enums.BossPhase;
import model.enums.CombatTag;
import model.enums.enemy.EnemyStats;
import model.enums.status.StatusEffects;
import model.enums.type.EnemyType;
import model.interfaces.Boss;
import utility.Utility;

import java.util.Random;

/**
 * Represents the Demon Lord, a boss-type enemy in a combat system. The Demon Lord
 * features unique combat behaviors, including multiple phases, random dice-based abilities,
 * and a powerful casting mechanism.
 *
 * The Demon Lord is initialized with predefined attributes such as health, attack, defense,
 * and rewards, which are derived from the EnemyStats configuration. Additionally, it has
 * a natural progression to Phase Two when health decreases below certain thresholds.
 *
 * The boss phases and casting mechanics add dynamic gameplay.
 */
public class DemonLord extends Enemy implements Boss {

    private BossPhase phase = BossPhase.PHASE_ONE;
    private int castTurnsRemaining = 2;
    private boolean isCasting = false;

    private int currentTurn = 0;

    /**
     * Constructs a new DemonLord instance with the specified name.
     * The Demon Lord uses pre-defined attributes such as maximum health, attack, defense, and rewards
     * from the EnemyStats for a Demon Lord. Additionally, it assigns a consumable item drop.
     */
    public DemonLord(String name) {
        super(name,
                EnemyStats.DEMON_LORD.getBaseMaxHp(),
                EnemyStats.DEMON_LORD.getBaseAttack(),
                EnemyStats.DEMON_LORD.getBaseDefence(),
                EnemyStats.DEMON_LORD.getGoldReward(),
                EnemyStats.DEMON_LORD.getXpReward(),
                Items.HEALTH_CHALICE.getItem()
        );
    }

    /**
     * Constructs a new DemonLord instance with the specified name and level.
     * The Demon Lord uses pre-defined attributes such as maximum health, attack, defense, and rewards
     * from the EnemyStats for a Demon Lord. Additionally, it assigns a consumable item drop.
     */
    public DemonLord(String name, int level) {
        super(name,
                EnemyStats.DEMON_LORD.getBaseMaxHp(),
                EnemyStats.DEMON_LORD.getBaseAttack(),
                EnemyStats.DEMON_LORD.getBaseDefence(),
                EnemyStats.DEMON_LORD.getGoldReward(),
                EnemyStats.DEMON_LORD.getXpReward(),
                level,
                Items.HEALTH_CHALICE.getItem()
        );
    }

    @Override
    public CombatTag getCombatTag() {
        return CombatTag.DEMON;
    }

    @Override
    public EnemyType getEnemyType() {
        return EnemyType.BOSS;
    }

    /**
     * Performs the Demon Lord's basic ability by dealing damage to the target character.
     */
    @Override
    public void performBasicAbility(Character target) {
        int damage = target.takeDamage((int)(this.getTotalAttack() * 1.5), this);
        this.damageAbilitySystemOut(damage, false);
    }

    /**
     * Executes the Demon Lord's special ability based on a random dice roll ranging from 1 to 6.
     * Each dice result triggers a specific action that either affects the Demon Lord or the target character.
     */
    @Override
    public void performSpecialAbility(Character target) {
        Random random = new Random();
        int diceRoll = random.nextInt(6) + 1;

        switch (diceRoll) {
            case 1, 3 -> {
                this.takeTrueDamage(10);
                this.print("The dice turn against the Demon Lord. Taking 10 true damage!");
            }
            case 2, 5 -> {
                target.takeTrueDamage(10);
                this.print("The dice glow red with destructive power!");
            }
            case 4 -> {
                int damage = target.takeTrueDamage(this.getAttack() * 2);
                this.trueDamageAbilitySystemOut(damage, false);
            }
            case 6 -> {
                this.applyStatusEffect(StatusEffects.THORNS, 4);
                this.print("Perfect roll! Dark power coils around the Demon Lord!");
            }
            default -> this.print("The dice lands on " + diceRoll + "!");
        }
    }

    /**
     * Executes the actions performed by the boss during its turn.
     * The behavior depends on the current phase of the boss and whether it is casting an ability.
     */
    @Override
    public void onBossTurn(Player player) {
        this.currentTurn++;

        if (this.isCasting) {
            this.continueCasting(player);
            return;
        }

        if (this.shouldStartCasting()) {
            this.startCasting();
        }

        if (this.phase == BossPhase.PHASE_ONE) {
            this.phaseOne(player);
        } else {
            this.phaseTwo(player);
        }

        //Demon lord casting bal bla output
    }

    private void phaseOne(Player player) {
        if (this.getHp() < this.getMaxHp() / 2) {
            this.phase = BossPhase.PHASE_TWO;
        }

        if (this.currentTurn % 2 == 0) {
            this.performSpecialAbility(player);
        } else {
            this.performBasicAbility(player);
        }
    }

    private void phaseTwo(Player player) {
        if (!player.hasStatusEffect(StatusEffects.DEMONLORD_CURSE)) {
            player.applyStatusEffect(StatusEffects.DEMONLORD_CURSE, 2);
        } else if (this.isCasting) {
            this.performBasicAbility(player);
        } else {
            this.performSpecialAbility(player);
        }
    }

    private void startCasting() {
        this.isCasting = true;
        this.castTurnsRemaining = 2;
        this.print("The Demon Lord begins casting a dark magic...");
    }

    private void continueCasting(Player player) {
        this.castTurnsRemaining--;
        if (this.castTurnsRemaining > 0) {
            this.print("The Demon Lord continues casting...");
        } else {
            this.finishCast(player);
        }
    }

    private void finishCast(Player player) {
        this.isCasting = false;
        this.print("The Demon Lord unleashes Hellfire!");

        if (player.isUntargetable() || player.hasStatusEffect(StatusEffects.INVISIBILITY)) {
            int damage = player.takeTrueDamage(this.getTotalAttack() * 3);
            this.trueDamageAbilitySystemOut(damage, true);
            return;
        }

        int damage = player.takeTrueDamage(this.getTotalAttack() * 4);
        this.trueDamageAbilitySystemOut(damage, false);
    }

    private boolean shouldStartCasting() {
        return this.currentTurn % 4 == 0;
    }
}
