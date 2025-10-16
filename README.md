<h2 align="center">
  <br>
  <img width="2795" height="300" alt="ascii-art-text" src="https://github.com/user-attachments/assets/ea3d13ee-492b-4d05-ad4d-51b12bc6d672" />
  <br>
  <br>
     Semester Console Roguelike Project in Java
  <br>
  <br>
</h2>

If you're on Linux(dnf), just run the commands below. <br>
If you're on Windows, you'll need to figure it out (or switch to Linux :P).

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

