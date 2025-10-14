import model.Player;
import model.enums.GameState;
import util.MenuLogic;

void main() throws IOException, InterruptedException {
    Scanner input = new Scanner(System.in);
    model.Player player = null;
    GameState state = GameState.MAIN_MENU;

    while (state != GameState.EXIT) {
        switch (state){
            case MAIN_MENU -> state = handleMenu(input, player);
            case CHARACTER_CREATION -> {
                player = MenuLogic.handleCharacterCreation(input);
                state = GameState.GAME;
            }
            case GAME -> state = handleGame(input, player);
            case DUNGEON -> state = handleDungeon(input, player);   //TODO: Implement dungeon
            case LABYRINTH -> state = handleLabyrinth(input, player);   //TODO: Implement labyrinth
            case INVENTORY -> state = handleInventory(input, player);   //TODO: Working but fi logic
            case STATS -> state = handleStats(input, player);
            case SETTINGS -> {
                //TODO: Implement settings
                state = GameState.MAIN_MENU;
            }
        }
    }
}

private GameState handleMenu(Scanner input, model.Player player) {
    int choice = MenuLogic.showMainMenu(input);
    return switch (choice){
        case 1 -> player == null ? GameState.CHARACTER_CREATION : GameState.GAME;
        case 2 -> GameState.SETTINGS;
        case 0 -> GameState.EXIT;
        default -> GameState.MAIN_MENU;
    };
}

private GameState handleGame(Scanner input, model.Player player) {
    int choice = MenuLogic.showGameMenu(input, player);
    return switch (choice) {
        case 1 -> GameState.DUNGEON;
        case 2 -> GameState.LABYRINTH;
        case 3 -> GameState.INVENTORY;
        case 4 -> GameState.STATS;
        case 0 -> GameState.MAIN_MENU;
        default -> GameState.GAME;
    };
}

private GameState handleDungeon(Scanner input, Player player) {
    System.out.println("\n=== DUNGEON ===");
    System.out.println("Dungeon gameplay not implemented yet");
    System.out.println("Press Enter to return...");
    input.nextLine();
    return GameState.GAME;
}

private GameState handleLabyrinth(Scanner input, Player player) {
    System.out.println("\n=== RANDOM LABYRINTH ===");
    System.out.println("Labyrinth not implemented yet");
    System.out.println("Press Enter to return...");
    input.nextLine();
    return GameState.GAME;
}

private GameState handleInventory(Scanner input, Player player) {
    MenuLogic.showInventoryMenu(input, player);
    return GameState.GAME;
}

private GameState handleStats(Scanner input, Player player) {
    System.out.println("\n=== STATS ===");
    player.displayStats();
    System.out.print("\nPress Enter to return...");
    input.nextLine();
    return GameState.GAME;
}