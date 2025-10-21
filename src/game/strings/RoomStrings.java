package game.strings;

public class RoomStrings {
    public static final String DUNGEON_ROOM_ONE_MAP = """
            ======== MAP ========
            
                     Door
                   /
                 E         ?
                   \\     /
                     You
            
            =====================
            """;
    public static final String DUNGEON_ROOM_TWO_MAP = """
            ======== MAP ========
            
                     Door
                   /
                 ?         ?
                   \\     /
                     You
            
            =====================
            """;
    public static final String DUNGEON_ROOM_THREE_MAP = """
            ======== MAP ========
            
                     {Door}
                   {/}
                 {E}
                 {|}
                 ?         ?
                   \\     /
                     You
            
            =====================
            """;

    public static final String DUNGEON_ROOM_FOUR_MAP = """
            ======== MAP ========
                     {Door}
                      {2|}
                      {2?}
                      {|}
                      {?}
                   {/}  {"\\"}
                 ?         ?
                   \\     /
                     You
            
            =====================
            """;
    public static final String DUNGEON_ROOM_FIVE_MAP = """
            ======== MAP ========

                     BOSS
                      |
                     You
            
            =====================
            """;
}
