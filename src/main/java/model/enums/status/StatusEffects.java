package model.enums.status;

import model.Character;
import model.players.Mage;
import model.players.Rogue;

/**
 * StackOverflow helped me here a lot with the enum implementation.
 * Especially the ability to have functions inside a specific status. (I didn't know that was possible)
 * So I don't have to use magic numbers in Character class.
 * https://stackoverflow.com/questions/2457076/can-i-add-a-function-to-enums-in-java
 *
 * Represents various status effects that can be applied to entities in the game.
 * Each status effect has a name and an optional description providing details about its effects.
 * Each effect has its own
 */
public enum StatusEffects {
    STRENGTH("Strength", "+25% Damage") {
        @Override
        public void onApply(Character target) {
            target.setDamageMultiplier(target.getDamageMultiplier() + 0.25);
        }

        @Override
        public void onRemove(Character target) {
            target.setDamageMultiplier(target.getDamageMultiplier() - 0.25);
        }
    },
    INVISIBILITY("Invisible", "Invisible") {
        @Override
        public void onTick(Character target) {
            if (target instanceof Mage mage) {
                if (Math.random() <= mage.getInvisibilityChance()) {
                    mage.print("You remain invisible!");
                    mage.decreaseInvisibilityChance();
                } else {
                    mage.print("You are no longer invisible!");
                    mage.removeStatusEffect(this);
                }
            }
        }

        @Override
        public void onRemove(Character target) {
            if (target instanceof Mage mage) {
                mage.resetInvisibility();
                mage.applyStatusEffect(StatusEffects.SHIELD, 1);
                mage.print("Small shield applied");
            }
        }
    },
    VANISH( "Vanish", "Untargetable") {
        @Override
        public void onTick(Character target) {
            if (target instanceof Rogue rogue) {
                if (rogue.getVanishTurns() <= 2) {
                    rogue.print("You remain untargetable!");
                    rogue.incrementVanishTurns();
                    return;
                }

                if (Math.random() <= rogue.getVanishChance()) {
                    rogue.print("You remain untargetable!");
                    rogue.print("");
                    rogue.decreaseVanishChance();
                } else {
                    rogue.removeStatusEffect(this);
                }
            }
        }

        @Override
        public void onRemove(Character target) {
            if (target instanceof Rogue rogue) {
                rogue.resetVanish();
            }
        }
    },
    SHIELD("Shield", "+15% maxHP shield") {
        @Override
        public void onApply(Character target) {
            target.setShield((target.getMaxHp() * 15) / 100);
        }

        @Override
        public void onRemove(Character target) {
            target.setShield(0);
        }
    },
    HEALING("Healing", "Small HoT") {
        @Override
        public void onTick(Character target) {
            target.heal((target.getMaxHp() / 100) * 2);
        }
    },
    BLEEDING("Bleeding", "-2% maxHP per turn") {
        @Override
        public void onTick(Character target) {
            target.takeTrueDamage((target.getMaxHp() / 100) * 2);
        }
    },
    THORNS("Thorns", "Reflecting 5% damage") {
        @Override
        public double getReflectionPercent() {
            return 0.05;
        }
    },

    // Skeleton
    SKELETON_CURSE("Skeleton Curse", "Losing 1% maxHP per turn") {
        @Override
        public void onTick(Character target) {
            target.takeTrueDamage((target.getMaxHp() / 100));
        }
    },

    // Troll

    // Elf
    ELF_STRENGTH("Strength", "Strength +20% Damage") {
        @Override
        public void onApply(Character target) {
            target.setDamageMultiplier(target.getDamageMultiplier() + 0.2);
        }

        @Override
        public void onRemove(Character target) {
            target.setDamageMultiplier(target.getDamageMultiplier() - 0.2);
        }
    },
    // Dragon

    // DemonLord
    DEMONLORD_CURSE("DemonLord Curse", "-20% Damage reduction") {
        @Override
        public void onApply(Character target) {
            target.setDamageMultiplier(target.getDamageMultiplier() - 0.2);
        }

        @Override
        public void onRemove(Character target) {
            target.setDamageMultiplier(target.getDamageMultiplier() + 0.2);
        }
    },

    // Warrior Stances as Buff
    AGGRESSIVE("Aggressive", "+20% Damage") {
        @Override
        public void onApply(Character target) {
            target.setDamageMultiplier(target.getDamageMultiplier() + 0.2);
        }

        @Override
        public void onRemove(Character target) {
            target.setDamageMultiplier(target.getDamageMultiplier() - 0.2);
        }
    },
    BALANCED("Balanced", "+15% Damage & Defence") {
        @Override
        public void onApply(Character target) {
            target.setDamageMultiplier(target.getDamageMultiplier() + 0.15);
            target.setDefenceMultiplier(target.getDefenceMultiplier() + 0.15);
        }

        @Override
        public void onRemove(Character target) {
            target.setDamageMultiplier(target.getDamageMultiplier() - 0.15);
            target.setDefenceMultiplier(target.getDefenceMultiplier() - 0.15);
        }
    },
    DEFENSIVE("Defensive", "+20% Defence, Thorn Damage") {
        @Override
        public void onApply(Character target) {
            target.setDefenceMultiplier(target.getDefenceMultiplier() + 0.2);
        }
        @Override
        public void onRemove(Character target) {
            target.setDefenceMultiplier(target.getDefenceMultiplier() - 0.2);
        }
        @Override
        public double getReflectionPercent() {
            return 0.20;
        }
    };

    private final String name;
    private final String description;

    StatusEffects(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    /**
     * Applies the status effect to the specified target character. This method
     * defines what occurs when the effect is initially applied to the target.
     */
    public void onApply(Character target) {

    }

    /**
     * Defines the behavior of the status effect on a character when a game tick occurs.
     * This method is called periodically to apply ongoing or
     * recurring effects of the status effect on the specified target.
     */
    public void onTick(Character target) {

    }

    /**
     * Defines the behavior of the status effect when it is removed from the target character.
     * This method outlines any cleanup, reversal, or concluding actions to be taken
     * when the status effect ends.
     */
    public void onRemove(Character target) {

    }

    public double getReflectionPercent() {
        return 0;
    }

}
