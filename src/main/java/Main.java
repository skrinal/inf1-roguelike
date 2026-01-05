import game.GameLogic;
import game.MenuLogic;
import model.Player;
import model.enums.GameState;
import model.strings.MenuStrings;
import utility.Utility;

import java.util.Scanner;

/**
 * The Main class serves as the entry point for the application,
 * managing the game's main loop and transitioning between various game states.
 * It handles the flow of the program by instances of MenuLogic and GameLogic,
 * as well as transitioning the player through different states based on their interactions.
 *
 * The game states managed by this class include:
 * - MAIN_MENU: The main menu of the game.
 * - CHARACTER_CREATION: The state for player character creation.
 * - GAME: The primary gameplay loop.
 * - DUNGEON: The dungeon exploration state.
 * - STATS: The state for viewing and managing player statistics.
 * - DEATH: The state triggered when the player character dies.
 * - COMPLETE: The state indicating game completion.
 * - EXIT: The state to terminate the game.
 */
public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Player player = null;

        GameState state = GameState.MAIN_MENU;

        MenuLogic menuLogic = new MenuLogic();
        GameLogic gameLogic = new GameLogic();

        while (state != GameState.EXIT) {
            switch (state) {
                case MAIN_MENU -> state = menuLogic.handleMenu(player);
                case CHARACTER_CREATION -> {
                    player = menuLogic.handleCharacterCreation(input);
                    state = GameState.GAME;
                }
                case GAME -> state = gameLogic.handleGame();
                case DUNGEON -> state = gameLogic.handleDungeon(player);

                case STATS -> state = player.handleStats();
                case DEATH -> {
                    player = null;
                    state = GameState.MAIN_MENU;

                    System.out.println(MenuStrings.GAME_OVER.getText());

                    Utility.enterToContinue();
                }
                case COMPLETE -> {
                    System.out.println(MenuStrings.VICTORY.getText());
                    Utility.enterToContinue();

                    System.out.println(MenuStrings.MORE_TO_COME.getText());
                    Utility.enterToContinue();

                    state = GameState.MAIN_MENU;
                }
                default -> {
                    System.out.println("Invalid state");
                    state = GameState.MAIN_MENU;
                }
            }
        }
    }
}
