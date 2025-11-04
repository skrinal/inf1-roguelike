package model.players;

import model.Character;
import model.Item;
import model.Player;
import model.enums.ClassPower;
import model.enums.ItemType;
import model.enums.PlayerClass;

import java.util.Random;

public class Mage extends Player {
    private final Item itemd = new Item("Kraken", ItemType.WEAPON, 5);

    private static final int MAX_HP = 80;
    private static final int ATTACK = 10;
    private static final int DEFENCE = 3;
    private static final int POWER = 100;

    public Mage(String name) {
        super(name, MAX_HP, ATTACK, DEFENCE, POWER);
        //this.setEquippedWeapon(new Item("Wooden Staff", ItemType.WEAPON, 5));
        //this.addItem(itemd);
        //this.equipedArmor = new Item("Leather Armor", ItemType.ARMOR, 4);
    }

    @Override
    public String getPowerString() {
        return ClassPower.MANA.name();
    }

    @Override
    public PlayerClass getClassType() {
        return PlayerClass.MAGE;
    }

//    @Override
//    public void displayStats() {
//        System.out.println("╔═════════════════════════════════╗");
//        System.out.println("║  " + this.getName().toUpperCase() + " [" + PlayerClass.MAGE + "]");
//        System.out.println("╠═════════════════════════════════╣");
//        System.out.println("║ HP:      [" + this.getHealthBar() + "] " + this.getHp() + "/" + this.getMaxHp());
//        System.out.println("║ " + this.getPowerName() + ":    [" + this.getPowerBar() + "] " + this.getPower() + "/" + this.getMaxPower());
//        System.out.println("║ Attack:  " + this.getTotalAttack());
//        System.out.println("║ Defense: " + this.getTotalDefense());
//        System.out.println("║ Gold:    " + this.getGold());
//        System.out.println("║ ");
//        System.out.println("║ Level:   " + this.getLevel() + " - " + this.getExperience() + "/" + this.getExperienceToNextLevel());
//        System.out.println("║ ");
//        System.out.println("║ Armor:   " + (this.getEquippedArmor() == null ? "None" : this.getEquippedArmor().getName() + " + " + this.getEquippedArmor().getValue() + " Armor"));
//        System.out.println("║ Weapon:  " + (this.getEquippedWeapon() == null ? "None" : this.getEquippedWeapon().getName() + " + " + this.getEquippedWeapon().getValue() + " Damage" ));
//        System.out.println("╚═════════════════════════════════╝");
//    }

    // TODO: Damage logic is bad
    @Override
    public void performSpecialAbility(Character target) {
        if (usePower(30)) {
            int damage = target.getTotalDefense() * 2 + new Random().nextInt(10);
            target.takeDamage(damage);
            System.out.println("FIREBALL! You blast " + target.getName() + " for " + damage + " damage!");
        } else {
            System.out.println("Not enough Mana!");
        }
    }


}
