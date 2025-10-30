import game.GameLogic;
import game.MenuLogic;
import model.Player;
import model.enums.GameState;
import java.io.IOException;
import java.util.Scanner;

void main() throws IOException, InterruptedException {
    Scanner input = new Scanner(System.in);
    Player player = null;
    GameState state = GameState.MAIN_MENU;

    while (state != GameState.EXIT) {
        switch (state){
            case MAIN_MENU -> state = handleMenu(input, player);
            case CHARACTER_CREATION -> {
                player = MenuLogic.handleCharacterCreation(input);
                state = GameState.GAME;
            }
            case GAME -> state = handleGame(input);
            case DUNGEON -> state = handleDungeon(input, player);   //TODO: Implement dungeon
//            case LABYRINTH -> state = handleLabyrinth(input, player);   //TODO: Implement labyrinth (INF2)
            case INVENTORY -> state = handleInventory(input, player);   //TODO: Working but fi logic
            case STATS -> state = handleStats(input, player);
            //TODO: Implement settings (INF 2) - > Save/Load, Difficulty
//            case SETTINGS -> {
//                state = GameState.MAIN_MENU;
//            }
            case DEATH -> {
                System.out.println("\n=== GAME OVER ===");
                System.out.println("You have died!");
                System.out.println("Press Enter to return to main menu...");
                input.nextLine();
                player = null;
                state = GameState.MAIN_MENU;
            }
            case COMPLETE -> {
                //TODO: VICTORY SCREEN
            }
        }
    }
}

private GameState handleMenu(Scanner input, Player player) {
    int choice = MenuLogic.showMainMenu(input);
    return switch (choice){
        case 1 -> player == null ? GameState.CHARACTER_CREATION : GameState.GAME;
        //case 2 -> GameState.SETTINGS;
        case 0 -> GameState.EXIT;
        default -> GameState.MAIN_MENU;
    };
}

private GameState handleGame(Scanner input) {
    int choice = MenuLogic.showGameMenu(input);
    return switch (choice) {
        case 1 -> GameState.DUNGEON;
        //case 2 -> GameState.LABYRINTH;
        case 3 -> GameState.INVENTORY;
        case 4 -> GameState.STATS;
        case 0 -> GameState.MAIN_MENU;
        default -> GameState.GAME;
    };
}

private GameState handleDungeon(Scanner input, Player player) {
    System.out.println("\n=== DUNGEON ===");
    GameState result = GameLogic.showGameMap(input, player);

    switch (result) {
        case DEATH -> {
            return GameState.DEATH;
        }
        case COMPLETE -> {
            return GameState.COMPLETE;
        }
        default -> { return GameState.MAIN_MENU; }
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

//public void displayCombatScreen() {
//    // Split screen into two columns
//    String leftColumn = buildPlayerStats();
//    String rightColumn = buildEnemyDisplay();
//
//    // Combine columns `side by side
//    displaySideBySide(leftColumn, rightColumn);
//    displayCombatOptions();
//}

//private String buildPlayerStats(model.Player player) {
//    StringBuilder sb = new StringBuilder();
//    sb.append("=== YOUR STATS ===\n");
//    sb.append("HP: ").append(player.getHealth()).append("/").append(player.getMaxHealth()).append("\n");
//    sb.append("Mana: ").append(player.getMana()).append("/").append(player.getMaxMana()).append("\n");
//    sb.append("Attack: ").append(player.getAttack()).append("\n");
//    sb.append("Defense: ").append(player.getDefense()).append("\n");
//    sb.append("Level: ").append(player.getLevel()).append("\n");
//    sb.append("XP: ").append(player.getExperience()).append("\n");
//    sb.append("\n");
//    sb.append("=== SPELLS ===\n");
//    for (int i = 0; i < player.getSpells().size(); i++) {
//        Spell spell = player.getSpells().get(i);
//        sb.append((i + 1) + ". " + spell.getName() + " (Cost: " + spell.getManaCost() + ")\n");
//    }
//    return sb.toString();
//}

//private String buildEnemyDisplay() {
//    StringBuilder sb = new StringBuilder();
//    sb.append("=== ").append(enemy.getName().toUpperCase()).append(" ===\n");
//    sb.append(enemy.getAsciiArt()).append("\n");
//    sb.append("HP: ").append(enemy.getHealth()).append("/").append(enemy.getMaxHealth()).append("\n");
//    sb.append("Attack: ").append(enemy.getAttack()).append("\n");
//    sb.append("Defense: ").append(enemy.getDefense()).append("\n");
//    sb.append("Type: ").append(enemy.getType()).append("\n");
//    return sb.toString();
//}

//private void displaySideBySide(String left, String right) {
//    String[] leftLines = left.split("\n");
//    String[] rightLines = right.split("\n");
//
//    int maxLines = Math.max(leftLines.length, rightLines.length);
//
//    for (int i = 0; i < maxLines; i++) {
//        String leftLine = i < leftLines.length ? leftLines[i] : "";
//        String rightLine = i < rightLines.length ? rightLines[i] : "";
//
//        // Format left column (fixed width)
//        String formattedLeft = String.format("%-40s", leftLine);
//        System.out.println(formattedLeft + "    " + rightLine);
//    }
//}