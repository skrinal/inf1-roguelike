package game.room.instance;


import game.room.RoomOutCome;
import game.room.combat.CombatSystem;
import model.Player;
import model.enums.room.RoomMap;
import java.util.Scanner;

public abstract class Room {
    private final Player player;
    private final CombatSystem combat;
    private final RoomMap display;

    public Room(Player player, CombatSystem combat, RoomMap display) {
        this.player = player;
        this.combat = combat;
        this.display = display;
    }

    public abstract RoomOutCome enter(Scanner input);

    protected void showRoomInfo() {
        System.out.println(this.display.getDescription());
        System.out.println(this.display.getMap());
    }

    protected Player getPlayer() {
        return this.player;
    }

    protected CombatSystem getCombat() {
        return this.combat;
    }
}
