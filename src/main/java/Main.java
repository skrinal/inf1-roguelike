import game.GameLogic;
import game.MenuLogic;
import model.Player;
import model.enums.GameState;
import model.strings.MenuStrings;
import utility.Utility;

void main() {
    Scanner input = new Scanner(System.in);
    Player player = null;

    GameState state = GameState.MAIN_MENU;

    MenuLogic menuLogic = new MenuLogic();
    GameLogic gameLogic = new GameLogic();

    while (state != GameState.EXIT) {
        switch (state){
            case MAIN_MENU -> state = menuLogic.handleMenu(player);
            case CHARACTER_CREATION -> {
                player = menuLogic.handleCharacterCreation(input);
                state = GameState.GAME;
            }
            case GAME -> state = gameLogic.handleGame();
            case LOAD_GAME -> {

            }
            case SAVE_GAME -> {
                if (player != null) {

                } else {
                    System.out.println("Nothing to be saved");
                }
            }
            case DUNGEON -> state = gameLogic.handleDungeon(player);

            //case INVENTORY -> state = handleInventory(input, player);

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
