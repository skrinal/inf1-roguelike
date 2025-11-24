package game.room.instance;

import game.room.RoomOutCome;
import game.room.combat.CombatSystem;
import model.Enemy;
import model.Item;
import model.Player;
import model.enums.room.RoomMap;

import java.util.ArrayList;
import java.util.Scanner;

public class RoomTwo extends Room {

    public RoomTwo(Player player, CombatSystem combat, RoomMap display, ArrayList<Enemy> enemies) {
        super(player, combat, display, enemies);
    }

    @Override
    public RoomOutCome enter(Scanner input) {
        while (true) {
            int choice;
        }
    }
}
