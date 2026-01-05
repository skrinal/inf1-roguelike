package model.strings;

/**
 * The MenuStrings enum contains predefined ASCII art or textual representations for
 * different menu states in the game. Each constant represents a specific menu or
 * a message text used within the game menus.
 */
public enum MenuStrings {

    GAME_TITLE_MENU("""
            ______                     _   _           ______            _             _
            |  ___|                   | | | |          | ___ \\          (_)           (_)
            | |_ _ __ ___  _ __ ___   | |_| |__   ___  | |_/ / ___  __ _ _ _ __  _ __  _ _ __   __ _
            |  _| '__/ _ \\| '_ ` _ \\  | __| '_ \\ / _ \\ | ___ \\/ _ \\/ _` | | '_ \\| '_ \\| | '_ \\ / _` |
            | | | | | (_) | | | | | | | |_| | | |  __/ | |_/ /  __/ (_| | | | | | | | | | | | | (_| |
            \\_| |_|  \\___/|_| |_| |_|  \\__|_| |_|\\___| \\____/ \\___|\\__, |_|_| |_|_| |_|_|_| |_|\\__, |
                                                                    __/ |                       __/ |
            ╔═══════════════════════════╗                          |___/                       |___/
            ║       1) Start Game       ║
            ║       0) Exit             ║
            ╚═══════════════════════════╝
            """),

    CHARACTER_CREATION("""
            
            ╔══════════════════════╗
            ║  CHARACTER CREATION  ║
            ╚══════════════════════╝
            """),

    CHOOSE_CLASS("""
            
            ╔════════════════╗
            ║  CHOOSE CLASS  ║
            ╚════════════════╝
            """),

    GAME_MENU_OPTIONS("""
            
            ╔═════════════╗
            ║  GAME MENU  ║
            ╚═════════════╝
            1) Dungeon
            2) Stats
            0) Back
            """),

    DUNGEON_TITLE("""
            
            ╔═════════════════╗
            ║  D U N G E O N  ║
            ╚═════════════════╝
            """),

    GAME_OVER("""
            
            ╔═══════════════════╗
            ║     GAME OVER     ║
            ║   You have died!  ║
            ╚═══════════════════╝
            """),

    VICTORY("""
            
            ╔════════════════════════════════════════════╗
            ║                                            ║
            ║                CONGRATULATIONS!            ║
            ║                                            ║
            ║    You have Managed to escape the dungeon  ║
            ║            and managed to kill             ║
            ║                                            ║
            ║             The Ashen Warden !!            ║
            ║                                            ║
            ║                                            ║
            ╚════════════════════════════════════════════╝
            """),

    MORE_TO_COME("""
            
            ╔════════════════════════════════════╗
            ║                                    ║
            ║     More content coming soon!      ║
            ║                                    ║
            ╚════════════════════════════════════╝
            """);

    private final String text;

    MenuStrings(String text) {
        this.text = text;
    }

    /**
     * Returns the text associated with this Enum constant.
     *
     * @return the text value of this Enum constant
     */
    public String getText() {
        return this.text;
    }
}
