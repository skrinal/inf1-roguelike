package model;

public abstract class Character {
    private String name;
    private int hp;
    private int maxHp;
    private int attack;
    private int defence;

    private int level;
    private int experience;
    private int experienceToNextLevel;

    public Character(String name, int maxHp, int attack, int defence) {
        this.name = name;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.attack = attack;
        this.defence = defence;
        this.level = 1;
        this.experience = 0;
        this.experienceToNextLevel = calculateExperienceToNextLevel();
    }

    public String getName() {return this.name;}

    public int getHp() {return this.hp;}

    public int getMaxHp() {return this.maxHp;}

    public int getAttack() {return this.attack;}

    public int getDefence() {return this.defence;}

    public boolean isAlive() {return this.hp > 0;}

    public void takeDamage(int damage) {
        int actualDamage = Math.max(1, damage - getTotalDefense());
        this.hp = Math.max(0, hp - actualDamage);
    }

    public void heal(int amount) {
        this.hp = Math.min(this.maxHp, this.hp + amount);
    }

    public int getLevel() {return this.level;}

    public int getExperience() {return this.experience;}

    public int getExperienceToNextLevel() {return this.experienceToNextLevel;}

    public void gainExperience(int amount) {
        this.experience += amount;
        while (this.experience >= this.experienceToNextLevel) {
            levelUp();
        }
    }

    private void levelUp() {
        this.experience -= this.experienceToNextLevel;
        this.level++;
        this.experienceToNextLevel = calculateExperienceToNextLevel();

        this.maxHp += 10 + (this.level * 2);
        this.hp = this.maxHp;
        this.attack += 2 + this.level;
        this.defence += 1 + (this.level / 2); // FIXME: Defense is int and we doing (level / 2) fix pls
    }

// TODO: Come up with system to lose experience
// public void loseExperience(int amount) {}

    private int calculateExperienceToNextLevel() {
        return (int) (100 * Math.pow(1.2, this.level));
    }

    public abstract int getTotalAttack();

    public abstract int getTotalDefense();

    public abstract void displayStats();

    public abstract void performSpecialAbility(Character target);

}
