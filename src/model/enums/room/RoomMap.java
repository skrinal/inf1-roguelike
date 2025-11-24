package model.enums.room;

public enum RoomMap {
    ROOM_ONE("""
            ======== MAP ========
                     Door
                   /
                 X         ?
                   \\     /
                     You
            =====================""",
            """
                    | You step into a dimly lit room.
                Two strange markings catch your eye on opposite walls
                — one shaped like an “X”, the other like a “?”."""
    ),
    ROOM_TWO("""
            ======== MAP ========
                     Door
                   /
                 X         ?
                   \\     /
                     You
            =====================""",
            """
                    | You step into a dimly lit room.
                Two strange markings catch your eye on opposite walls
                — one shaped like an “X”, the other like a “?”."""
    );

    private final String map;
    private final String description;
    RoomMap(String display, String description) {
        this.map = display;
        this.description = description;
    }

    public String getMap() {
        return this.map;
    }

    public String getDescription() {
        return this.description;
    }

}
