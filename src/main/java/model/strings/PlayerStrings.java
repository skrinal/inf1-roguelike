package model.strings;

/**
 * The PlayerStrings enum provides pre-defined string templates for displaying
 * specific player-related menus and statistics in a formatted manner.
 *
 * Each enum constant represents a unique section of the user interface, such as
 * player stats or inventory menu, and contains associated visual ASCII-based
 * formatting.
 */
public enum PlayerStrings {
    PLAYER_STATS("""
            
            ╔═════════╗
            ║  STATS  ║
            ╚═════════╝"""),


    PLAYER_INVENTORY_MENU("""
            
            ╔═══════════════════════╗
            ║    INVENTORY MENU     ║
            ╠═══════════════════════╣
            ║   1) Consumables      ║
            ║   2) Weapons          ║
            ║   3) Armor            ║
            ║   0) Exit Inventory   ║
            ╚═══════════════════════╝""");

    private final String menu;

    PlayerStrings(String menu) {
        this.menu = menu;
    }

    public String getMenu() {
        return this.menu;
    }
}
