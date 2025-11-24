import game.GameLogic;
import game.MenuLogic;
import model.Player;
import model.enums.GameState;

void main() {
    Scanner input = new Scanner(System.in);
    Player player = null;

    // TODO: skola
    GameState state = GameState.MAIN_MENU;

    MenuLogic menuLogic = new MenuLogic();
    GameLogic gameLogic = new GameLogic();

    while (state != GameState.EXIT) {
        switch (state){
            case MAIN_MENU -> state = menuLogic.handleMenu(input, player);
            case CHARACTER_CREATION -> {
                player = menuLogic.handleCharacterCreation(input);
                state = GameState.GAME;
            }
            case GAME -> state = gameLogic.handleGame(input);
            case DUNGEON -> state = gameLogic.handleDungeon(input, player);

            //case INVENTORY -> state = handleInventory(input, player);

            case STATS -> state = player.handleStats(input);
            case DEATH -> {
                player = null;
                state = GameState.MAIN_MENU;

                System.out.println("\n=== GAME OVER ===");
                System.out.println("You have died!");
                System.out.println("Press Enter to return to main menu...");

                input.nextLine();
            }
            case COMPLETE -> {
                //TODO: VICTORY SCREEN
            }
        }
    }
}



// (INF2)
//private GameState handleLabyrinth(Scanner input, Player player) {
//    System.out.println("\n=== RANDOM LABYRINTH ===");
//    System.out.println("Labyrinth not implemented yet");
//    System.out.println("Press Enter to return...");
//    input.nextLine();
//    return GameState.GAME;
//}

// (INF2)
//private GameState handleInventory(Scanner input, Player player) {
//    MenuLogic.showInventoryMenu(input, player);
//    return GameState.GAME;
//}
