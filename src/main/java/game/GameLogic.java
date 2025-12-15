package game;

import game.room.RoomOutCome;
import game.room.combat.CombatSystem;
import game.room.instance.Room;
import game.room.instance.RoomFactory;
import model.Player;
import model.enums.GameState;
import model.enums.room.RoomType;
import static utility.Utility.handleDecision;
import static model.strings.MenuStrings.GAME_MENU_OPTIONS;

public class GameLogic {
    private RoomFactory roomFactory = new RoomFactory();
    private CombatSystem combat = new CombatSystem();

    public GameState handleGame() {
        int choice = this.showGameMenu();
        return switch (choice) {
            case 1 -> GameState.DUNGEON;
            case 2 -> GameState.STATS;
            case 0 -> GameState.MAIN_MENU;
            default -> GameState.GAME;
        };
    }

    public GameState handleDungeon(Player player) {
        System.out.println("\n=== DUNGEON ===");
        GameState result = this.showGameMap(player);

        switch (result) {
            case DEATH -> {
                return GameState.DEATH;
            }
            case COMPLETE -> {
                return GameState.COMPLETE;
            }
            default -> {
                return GameState.MAIN_MENU;
            }
        }
    }

    private int showGameMenu() {
        System.out.println("\n" + GAME_MENU_OPTIONS);

        return handleDecision(0, 2);
    }

    private GameState showGameMap(Player player) {
        RoomType currentRoom = RoomType.ONE;
        boolean inDungeon = true;

        while (inDungeon) {
            Room room = this.createRoom(currentRoom, player);
            RoomOutCome outCome = room.enter();

            switch (outCome.result()) {
                case EXIT -> {
                    return GameState.MAIN_MENU;
                }
                case DEATH -> {
                    return GameState.DEATH;
                }
                case COMPLETED -> {
                    currentRoom = outCome.nextRoom();
                    if (currentRoom == RoomType.FIVE) {
                        return GameState.COMPLETE;
                    }
                }
                default -> inDungeon = false;
            }
        }
        return GameState.COMPLETE; // Completed dungeon
    }

    private Room createRoom(RoomType type, Player player) {
        return this.roomFactory.createRoom(type, player, this.combat);
    }

}