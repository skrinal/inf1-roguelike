package model.interfaces;

import model.Character;
import model.enums.type.EnemyType;

/**
 * Interface for enemies that can perform spectral damage.
 */
public interface SpectralAttacker {

    int getTotalAttack();
    EnemyType getEnemyType();
    void trueDamageAbilitySystemOut(int damage, boolean isSpectral);

    default void performSpectralDamage(Character target) {
        if (!this.getEnemyType().isSpectral()) {
            return; //Just in case so trash mobs by accident don't have spectral :D
        }

        double multiplier = this.getEnemyType().getDamagePercentage() / 100.0;
        int damage = target.takeTrueDamage((int)(this.getTotalAttack() * multiplier));

        this.trueDamageAbilitySystemOut(damage, true);
    }
}
