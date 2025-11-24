package model;

public abstract class Character {
    private final String name;
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
        this.experienceToNextLevel = this.calculateExperienceToNextLevel();
    }

    public String getName() {
        return this.name;
    }

    public int getHp() {
        return this.hp;
    }

    protected void setHp(int hp) {
        this.hp = hp;
    }

    public int getMaxHp() {
        return this.maxHp;
    }

    protected void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getAttack() {
        return this.attack;
    }

    protected void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefence() {
        return this.defence;
    }

    protected void setDefence(int defence) {
        this.defence = defence;
    }

    public boolean isAlive() {
        return this.hp > 0;
    }

    //TODO: UML ZMENA
    public int takeDamage(int damage) {
        int actualDamage = Math.max(1, damage - this.getTotalDefense());
        this.setHp(Math.max(0, this.hp - actualDamage));
        return actualDamage;
    }

    public void heal(int amount) {
        this.hp = Math.min(this.maxHp, this.hp + amount);
    }

    public int getLevel() {
        return this.level;
    }
    
    //TODO: preco tu je protected ?
    protected void incrementLevel() {
        this.level++;
    }

    public int getExperience() {
        return this.experience;
    }

    public int getExperienceToNextLevel() {
        return this.experienceToNextLevel;
    }

    public void gainExperience(int amount) {
        this.experience += amount;
        while (this.experience >= this.experienceToNextLevel) {
            this.levelUp();
        }
    }

    private void levelUp() {
        this.experience -= this.experienceToNextLevel;
        this.level++;
        this.experienceToNextLevel = this.calculateExperienceToNextLevel();

        this.maxHp += 10 + (this.level * 2);
        this.hp = this.maxHp;
        this.attack += 2 + this.level;
        this.defence += 1 + (int)((double)this.level / 2);
    }

    public String getHealthBar() {
        return this.getBar(this.getHp(), this.getMaxHp());
    }

    protected String getBar(int current, int max) {
        int bars = (int)((double)current / max * 10);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            result.append(i < bars ? "█" : "░");
        }
        return result.toString();
    }

// TODO: Come up with system to lose experience
// public void loseExperience(int amount) {}

    private int calculateExperienceToNextLevel() {
        return (int)(100 * Math.pow(1.2, this.level));
    }

    public abstract int getTotalAttack();
    public abstract int getTotalDefense();

    public abstract void performeBasicAbility(Character target);
    public abstract void performeSpecialAbility(Character target);

}
