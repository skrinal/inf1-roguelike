package model.enemies;

import model.Character;
import model.Enemy;
import model.Player;
import model.enums.BossPhase;
import model.enums.CombatTag;
import model.enums.status.StatusEffects;
import model.enums.type.EnemyType;
import model.interfaces.Boss;
import model.interfaces.SpectralAttacker;
import utility.Utility;

public class DemonLord extends Enemy implements SpectralAttacker, Boss {

    private static final int MAX_HP = 5;
    private static final int ATTACK = 30;
    private static final int DEFENCE = 20;
    private static final int GOLD_REWARD = 100;

    private BossPhase phase = BossPhase.PHASE_ONE;
    private int castTurnsRemaining = 2;
    private boolean isCasting = false;

    private int currentTurn = 0;

    public DemonLord(String name) {
        super(name, MAX_HP, ATTACK, DEFENCE, GOLD_REWARD);
    }
    public DemonLord(String name, int level) {
        super(name, MAX_HP, ATTACK, DEFENCE, GOLD_REWARD, level);
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
            case 1, 3, 5 -> {
                this.takeTrueDamage(5);
                System.out.println("The dice turn against the Demon Lord.");
            }
            case 2, 4 -> {
                target.takeTrueDamage(10);
                System.out.println("The dice glow red with destructive power!");
            }
            case 6 -> {
                this.applyStatusEffect(StatusEffects.THORNS, 4);
                System.out.println("Perfect roll! Dark power coils around the Demon Lord!");
            }
        }
        int damage = target.takeTrueDamage(this.getAttack() * 2);
        this.trueDamageAbilitySystemOut(damage, false);
    }

    @Override
    public void performSpectralDamage(Character target) {

        int damage = target.takeDamage((this.getTotalAttack() * this.getEnemyType().getDamagePercentage()) / 100, this); // 50% normal Damage
        this.damageAbilitySystemOut(damage, true);
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
        System.out.println("The Demon Lord begins casting a dark magic...");
    }

    private void continueCasting(Player player) {
        this.castTurnsRemaining--;
        if (this.castTurnsRemaining > 0) {
            System.out.println("The Demon Lord continues casting...");
        } else {
            this.finishCast(player);
        }
    }

    private void finishCast(Player player) {
        this.isCasting = false;
        System.out.println("The Demon Lord unleashes Hellfire!");

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
