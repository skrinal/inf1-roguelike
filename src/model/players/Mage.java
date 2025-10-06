package model.players;

import model.Character;
import model.Item;
import model.Player;
import model.enums.ItemType;

import java.util.Random;

public class Mage extends Player {
    public Mage(String name) {
        super(name, 80, 8, 3, 100);
        this.equipedWeapond = new Item("Wooden Staff", ItemType.WEAPON, 5);
    }

    @Override
    public String getPowerName() {
        return "Mana";
    }

    @Override
    public int getTotalDefense() {
        return 0;
    }

    @Override
    public void displayStats() {
        System.out.println("╔══════════════════════════════╗");
        System.out.println("║  " + name.toUpperCase() + " [MAGE]");
        System.out.println("╠══════════════════════════════╣");
        System.out.println("║ HP:      [" + getHealthBar() + "] " + hp + "/" + maxHp);
        System.out.println("║ Mana:    [" + getPowerBar() + "] " + power + "/" + maxPower);
        System.out.println("║ Attack:  " + getTotalAttack());
        System.out.println("║ Defense: " + getTotalDefense());
        System.out.println("║ Gold:    " + gold);
        System.out.println("╚══════════════════════════════╝");
    }

    @Override
    public void performSpecialAbility(Character target) {
        if (usePower(30)) {
            int damage = getTotalDefense() * 2 + new Random().nextInt(10);
            target.takeDamage(damage);
            IO.println("FIREBALL! You blast" + target.getName() + " for " + damage + " damage!");
        } else {
            IO.println("Not enough Mana!");
        }
    }

    private String getBar(int current, int max){
        int bars = (current * max) / max;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            result.append(i < bars ? "█" : "░");
        }
        return result.toString();
    }
}
