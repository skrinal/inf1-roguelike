package model.enemies;

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

public class DemonLord extends Enemy implements Boss {

    private BossPhase phase = BossPhase.PHASE_ONE;
    private int castTurnsRemaining = 2;
    private boolean isCasting = false;

    private int currentTurn = 0;

    public DemonLord(String name) {
        super(name,
                EnemyStats.DEMON_LORD.getBaseMaxHp(),
                EnemyStats.DEMON_LORD.getBaseAttack(),
                EnemyStats.DEMON_LORD.getBaseDefence(),
                EnemyStats.DEMON_LORD.getGoldReward(),
                EnemyStats.DEMON_LORD.getXpReward()
        );
    }
    public DemonLord(String name, int level) {
        super(name,
                EnemyStats.DEMON_LORD.getBaseMaxHp(),
                EnemyStats.DEMON_LORD.getBaseAttack(),
                EnemyStats.DEMON_LORD.getBaseDefence(),
                EnemyStats.DEMON_LORD.getGoldReward(),
                EnemyStats.DEMON_LORD.getXpReward(),
                level
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

    @Override
    public void performeBasicAbility(Character target) {
        int damage = target.takeDamage((int)(this.getTotalAttack() * 1.5), this);
        this.damageAbilitySystemOut(damage, false);
    }

    @Override
    public void performeSpecialAbility(Character target) {
        int diceRoll = Utility.getRandom().nextInt(6) + 1;
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
        }
    }

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

        switch (this.phase) {
            case PHASE_ONE -> this.phaseOne(player);
            case PHASE_TWO -> this.phaseTwo(player);
        }
        //Demon lord casting bal bla output
    }

    private void phaseOne(Player player) {
        if (this.getHp() < this.getMaxHp() / 2) {
            this.phase = BossPhase.PHASE_TWO;
        }

        if (this.currentTurn % 2 == 0) {
            this.performeSpecialAbility(player);
        } else {
            this.performeBasicAbility(player);
        }
    }

    private void phaseTwo(Player player) {
        if (!player.hasStatusEffect(StatusEffects.DEMONLORD_CURSE)) {
            player.applyStatusEffect(StatusEffects.DEMONLORD_CURSE, 3);
        } else if (this.isCasting) {
            this.performeBasicAbility(player);
        } else {
            this.performeSpecialAbility(player);
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

        if (!player.isUntargatable() || player.hasStatusEffect(StatusEffects.INVISIBILITY)) {
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
