package model.players;

import model.Character;
import model.Item;
import model.Player;
import model.enums.ClassPower;
import model.enums.ItemType;
import model.enums.PlayerClass;

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

    // TODO: Damage logic is bad
    @Override
    public void performeSpecialAbility(Character target) {
        if (usePower(50)) {
            int damage = (int)(this.getMaxPower() * 2.5);
            target.takeDamage(damage);
            System.out.println("FIREBLAST! You blast " + target.getName() + " for " + damage + " damage!");
        } else {
            System.out.println("Not enough Mana!");
        }
    }
    @Override
    public void performeBasicAbility(Character target) {
        if (usePower(10)) {
            int damage = (int)(this.getMaxPower() * 1.2);
            target.takeDamage(damage);
            System.out.println("Fireball! You blast " + target.getName() + " for " + damage + " damage!");



        } else {
            System.out.println("Not enough Mana!");
        }
    }

    @Override
    public void performeUtilityAbitlity() {

    }




}
