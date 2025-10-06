package model;

public abstract class Character {
    protected String name;
    protected int hp;
    protected int maxHp;
    protected int attack;
    protected int defence;

    protected int level;
    protected int experience;
    protected int experienceToNextLevel;

    public Character(String name, int maxHp, int attack, int defence) {
        this.name = name;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.attack = attack;
        this.defence = defence;
        this.level = 1;
        this.experience = 0;
        this.experienceToNextLevel = 0;
    }

    public String getName() {return name;}

    public int getHp() {return hp;}

    public int getMaxHp() {return maxHp;}

    public int getAttack() {return attack;}


    public int getDefence() {return defence;}

    public boolean isAlive() {return hp > 0;}

    public void takeDamage(int damage) {
        int actualDamage = Math.max(1, damage - defence);
        hp = Math.max(0, hp - actualDamage);
    }

    public void heal(int amount) {
        hp = Math.min(maxHp, hp + amount);
    }

    public int getLevel() {return level;}

    public int getExperience() {return experience;}

    public int getExperienceToNextLevel() {return experienceToNextLevel;}

    public void gainExperience(int amount) {
        experience += amount;
        while (experience >= experienceToNextLevel) {
            levelUp();
        }
    }

    private void levelUp() {
        experience = -experienceToNextLevel;
        level++;
        experienceToNextLevel = calculateExprenienceToNextLevel();

        maxHp += 10 + (level * 2);
        hp = maxHp;
        attack += 2 + level;
        defence += 1 + (level / 2); // FIXME: Defense is int and we doing (level / 2) fix pls
    }
// TODO: Come up with system to lose experience
// public void loseExperience(int amount) {}

    private int calculateExprenienceToNextLevel() {
        return (int) (100 * Math.pow(1.2, level));
    }

    public abstract int getTotalAttack();

    public abstract int getTotalDefense();

    public abstract void displayStats();

    public abstract void performSpecialAbility(Character target);

}
