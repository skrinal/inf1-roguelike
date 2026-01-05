package game;

import game.room.RoomOutCome;
import game.room.combat.CombatSystem;
import game.room.instance.Room;
import game.room.instance.RoomFactory;
import model.Player;
import model.enums.GameState;
import model.enums.room.RoomType;
import model.strings.MenuStrings;

import static utility.Utility.handleDecision;

/**
 * The GameLogic class controls the flow of the game, managing interactions between
 * the player and the game system.
 * It manages the main game logic, dungeon navigation, and connects the combat and room systems.
 */
public class GameLogic {
    private final RoomFactory roomFactory = new RoomFactory();
    private final CombatSystem combat = new CombatSystem();

    /**
     * Handles the game flow based on the player's choice.
     * @return
     */
    public GameState handleGame() {
        int choice = this.showGameMenu();
        return switch (choice) {
            case 1 -> GameState.DUNGEON;
            case 2 -> GameState.STATS;
            case 0 -> GameState.MAIN_MENU;
            default -> GameState.GAME;
        };
    }

    /**
     * Handles the dungeon flow based on the player's choice.
     * @param player
     * @return
     */
    public GameState handleDungeon(Player player) {
        System.out.println(MenuStrings.DUNGEON_TITLE.getText());
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
        System.out.println(MenuStrings.GAME_MENU_OPTIONS.getText());

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
                    if (outCome.nextRoom() == null) {
                        return GameState.COMPLETE;
                    }
                    currentRoom = outCome.nextRoom();
                }
                default -> inDungeon = false;
            }
        }
        return GameState.COMPLETE;
    }

    private Room createRoom(RoomType type, Player player) {
        return this.roomFactory.createRoom(type, player, this.combat);
    }

}