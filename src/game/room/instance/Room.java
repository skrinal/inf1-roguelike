package game.room.instance;

import data.ItemDatabase;
import game.room.RoomDisplay;
import game.room.RoomOutCome;
import game.room.combat.CombatSystem;
import model.Enemy;
import model.Player;
import model.enums.room.RoomMap;
import model.enums.room.RoomResult;
import model.enums.room.RoomType;
import java.util.Scanner;
import static utility.Utility.handleDecision;

public abstract class Room {
    protected final Player player;
    protected final CombatSystem combat;
    protected final RoomMap display;

    public Room(Player player, CombatSystem combat, RoomMap display) {
        this.player = player;
        this.combat = combat;
        this.display = display;
    }

    public abstract RoomOutCome enter(Scanner input);

    protected void showRoomInfo() {
        System.out.println(this.display.description);
        System.out.println(this.display.map);
    }
}
