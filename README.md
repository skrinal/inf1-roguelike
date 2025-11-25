<h2 align="center">
  <br>
    <p align="center">
        <img src="src/assets/banner.png" width="1080" height="210"/>
    </p>
     Semester Console Roguelike Project in Java
  <br>
  <br>
</h2>

If you're on Linux(dnf), just run the commands below. <br>
If you're on Windows, you'll need to figure it out (or switch to Linux :P).

## Features
✔️ Multiple player classes (Warrior, Rogue, Mage)

✔️ Turn-based combat

✔️ Inventory + Items

✔️ Enemies with unique behavior

⚠️ Saving/loading (still resolving some issues)

## Requirments
To clone and run this application, you'll need [Git](https://git-scm.com) and [Java 25](https://www.oracle.com/java/technologies/downloads/) 
```bash
# Update system packages
sudo dnf update

# Install Git
sudo dnf install git -y

# Install Java 25 (OpenJDK)
sudo dnf install java-25-openjdk-devel.x86_64 -y

# If you have an older Java version installed, switch to JDK 25
sudo alternatives --config java
```

## How To Run
```bash
# Clone the repository
git clone https://github.com/skrinal/inf1-roguelike

# Move into the source directory
cd inf1-roguelike/src

# Run the game
java Main.java
```
## Player Classes & Enemies
Each class in the game has unique stats and abilities. 
All classes share the same core mechanics (HP, Attack, Defence, Power), but their abilities define their playstyle.

| Stats   | Rogue | Mage | Warrior | x | Skeleton | DemonLord |
|:--------|:-----:|:----:|:-------:|---|----------|-----------|
| HP      |  100  |  80  |   100   | x |          |           |
| Attack  |   8   |  10  |    7    | x |          |           |
| Defence |   5   |  3   |    6    | x |          |           |
| Power   |  150  | 100  |   120   | x |          |           |

## 🗡️ Rogue
A fast and agile damage-dealer focused on burst attacks and evasive maneuvers

- **Basic ability: Sinister Strike**
- **Special ability: Vanish**
  - The rogue becomes untargetable. While vanished, they can strike without being hit in return. 
        Each round spent in Vanish slightly increases the chance that the effect ends
- **Utility ability: Dice roll**
  - Roll a die to gain a status effect.
    - Even number: No effect
    - Pair (2 or 4): Gain a healing buff
    - 6: Gain a damage buff

## 🛡️ Warrior
A strong and resilient frontline fighter focused on sustained damage and absorbing enemy attacks
- **Basic ability: Bloodthirst**
- **Special ability: Execute**
    - The warrior attempts to execute the enemy below 15% health
      - If successful, it kills the enemy and gains health
      - If unsuccessful, the enemy loses a small amount of health and suffers a DoT effect
- **Utility ability: Warstance**
    - The warrior shifts their stance to adapt to battle.
      - Defensive stance: Reduce incoming damage
      - Balanced stance: Slight healing over time
      - Aggressive stance: Increase attack power

## 🪄 Mage
A fragile but deadly spellcaster focused on high burst damage and quick spell combos
- **Basic ability: Smite**
- **Special ability: Fireblast**
    - Unleashes a fiery strike that can critically hit for high damage
    - Has a chance to apply a damage-over-time effect, burning the target over time
- **Utility ability: Ember Surge**
    - Ignites the mage’s weapon with fire, enhancing attacks
        - A Small chance to deal bonus critical damage and apply a burning DoT on enemies
