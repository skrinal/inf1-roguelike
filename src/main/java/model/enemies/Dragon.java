package model.enemies;

import data.Items;
import model.Character;
import model.Enemy;
import model.enums.CombatTag;
import model.enums.enemy.EnemyStats;
import model.enums.type.EnemyType;
import model.interfaces.SpectralAttacker;

public class Dragon extends Enemy implements SpectralAttacker {

    public Dragon(String name) {
        super(name,
                EnemyStats.DRAGON.getBaseMaxHp(),
                EnemyStats.DRAGON.getBaseAttack(),
                EnemyStats.DRAGON.getBaseDefence(),
                EnemyStats.DRAGON.getGoldReward(),
                EnemyStats.DRAGON.getXpReward(),
                Items.HEALTH_CHALICE.getItem()
        );
    }
    public Dragon(String name, int level) {
        super(name,
                EnemyStats.DRAGON.getBaseMaxHp(),
                EnemyStats.DRAGON.getBaseAttack(),
                EnemyStats.DRAGON.getBaseDefence(),
                EnemyStats.DRAGON.getGoldReward(),
                EnemyStats.DRAGON.getXpReward(),
                level,
                Items.HEALTH_CHALICE.getItem()
        );
    }

    @Override
    public EnemyType getEnemyType() {
        return EnemyType.ELITE;
    }

    @Override
    public CombatTag getCombatTag() {
        return CombatTag.DRAGON;
    }

    @Override
    public void performeBasicAbility(Character target) {

    }
    //TODO FINIS METHODS
    @Override
    public void performeSpecialAbility(Character target) {

    }
}
