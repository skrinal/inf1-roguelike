package game.room.instance;

import game.room.RoomOutCome;
import game.room.combat.CombatSystem;
import model.Enemy;
import model.Item;
import model.Player;
import model.enums.room.RoomMap;
import model.enums.room.RoomResult;
import model.enums.room.RoomType;
import utility.Utility;

import java.util.ArrayList;
import java.util.Scanner;

public class RoomOne extends Room {

    public RoomOne(Player player, CombatSystem combat, RoomMap display, ArrayList<Enemy> enemies) {
        super(player, combat, display, enemies);
    }

    @Override
    public RoomOutCome enter(Scanner input) {

        while (true) {
            this.showRoomInfo();

            System.out.println("1. Investigate 'X' marking " + (this.allEnemiesKilled() ? "(cleared)" : ""));
            System.out.println("2. Investigate '?' marking " + (this.isTreasureFound() ? "(found)" : ""));

            int choice = userInput();

            switch (choice) {
                case 0 -> {
                    return new RoomOutCome(RoomResult.EXIT, null); // back to menu
                }
                case 1 -> { // Enemy
                    if (!this.allEnemiesKilled()) {
                        if (this.getCombat().startCombat(this.getPlayer(), this.getEnemies().getFirst())) {
                            this.getEnemies().removeFirst();
                        } else {
                            return new RoomOutCome(RoomResult.DEATH, null);
                        }
                    } else {
                        System.out.println("Nothing left here.");
                        Utility.enterToContinue();
                    }
                }
                case 2 -> { // Treasure
                    this.treasureFound();
                }
                case 3 -> {
                    return new RoomOutCome(RoomResult.COMPLETED, RoomType.TWO);
                }
                default -> {
                    System.out.println("Invalid selection. Try again");
                    Utility.enterToContinue();
                }
            }

        }
    }
}

