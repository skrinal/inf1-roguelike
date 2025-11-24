package game.room.instance;

import game.room.RoomDisplay;
import game.room.RoomOutCome;
import game.room.combat.CombatSystem;
import model.Enemy;
import model.Player;
import model.enemies.SkeletonWarrior;
import model.enums.room.RoomMap;
import model.enums.room.RoomResult;
import model.enums.room.RoomType;

import java.util.ArrayList;
import java.util.Scanner;

import static utility.Utility.handleDecision;

public class RoomOne extends Room{
    private ArrayList<Enemy> enemies;
    private boolean treasureFound = false;

    public RoomOne(Player player, CombatSystem combat, RoomMap display) {
        super(player, combat, display);
        this.enemies = new ArrayList<>();

        this.enemies.add(new SkeletonWarrior("Maximus", 100));
        this.enemies.add(new SkeletonWarrior("2Maximus", 100));
    }

    private boolean allEnemiesKilled(){
        return this.enemies.isEmpty();
    }

    @Override
    public RoomOutCome enter(Scanner input) {


        while (true) {
            int choice;

            this.showRoomInfo();

            System.out.println("1. Investigate 'X' marking " + (this.allEnemiesKilled() ? "(cleared)" : ""));
            System.out.println("2. Investigate '?' marking " + (this.treasureFound ? "(found)" : ""));

            if (this.allEnemiesKilled()) {
                System.out.println("3. Proceed to next room");
                choice = handleDecision(input, 0, 3);

            } else {
                choice = handleDecision(input, 0, 2);
            }

            switch (choice) {
                case 0 -> {
                    return new RoomOutCome(RoomResult.EXIT, null); // back to menu
                }
                case 1 -> { // Enemy
                    if (!this.allEnemiesKilled()) {
                        if (this.combat.startCombat(input, player, this.enemies.getFirst())) {
                            this.enemies.removeFirst();
                        } else {
                            return new RoomOutCome(RoomResult.DEATH, null);
                        }
                    } else {
                        System.out.println("Nothing left here.");
                    }
                }
                case 2 -> { // Treasure
                    this.treasureFound(player);
                }
                case 3 -> {
                    return new RoomOutCome(RoomResult.COMPLETED, RoomType.TWO);
                }
                default -> {
                    System.out.println("Invalid selection. Try again");
                }
            }

        }
    }

    private void treasureFound(Player player){
        if (!this.treasureFound) {
            this.treasureFound = true;
            System.out.println("You found a treasure!");
            player.addGold(100);
        } else {
            System.out.println("You already found the treasure.");
        }
    }


}

