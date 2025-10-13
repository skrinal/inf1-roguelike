package model.players;

import model.Character;
import model.Player;
import model.enums.ClassPower;
import model.enums.PlayerClass;

import java.util.Random;

public class Mage extends Player {
    public Mage(String name) {
        super(name, 80, 8, 3, 100);
        //this.equippedWeapon = new Item("Wooden Staff", ItemType.WEAPON, 5);
        //this.equipedArmor = new Item("Leather Armor", ItemType.ARMOR, 4);
    }

    @Override
    public String getPowerName() {
        return ClassPower.MANA.name();
    }

    @Override
    public void displayStats() {
        System.out.println("╔═══════════════════════════════╗");
        System.out.println("║  " + getName().toUpperCase() + " [" + PlayerClass.MAGE + "]");
        System.out.println("╠═══════════════════════════════╣");
        System.out.println("║ HP:      [" + getHealthBar() + "] " + getHp() + "/" + getMaxHp());
        System.out.println("║ " + getPowerName() + ":    [" + getPowerBar() + "] " + getPower() + "/" + getMaxPower());
        System.out.println("║ Attack:  " + getTotalAttack());
        System.out.println("║ Defense: " + getTotalDefense());
        System.out.println("║ Gold:    " + getGold());
        System.out.println("╚═══════════════════════════════╝");
    }

    // TODO: Damage logic is bad
    @Override
    public void performSpecialAbility(Character target) {
        if (usePower(30)) {
            int damage = target.getTotalDefense() * 2 + new Random().nextInt(10);
            target.takeDamage(damage);
            IO.println("FIREBALL! You blast " + target.getName() + " for " + damage + " damage!");
        } else {
            IO.println("Not enough Mana!");
        }
    }

    private String getHealthBar() {
        return getBar(getHp(), getMaxHp());
    }

    private String getPowerBar() {
        return getBar(getPower(), getMaxPower());
    }

    private String getBar(int current, int max) {
        int bars = (current * max) / max;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            result.append(i < bars ? "█" : "░");
        }
        return result.toString();
    }
}
