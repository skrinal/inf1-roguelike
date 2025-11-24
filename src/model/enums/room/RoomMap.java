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
    );

    public final String map;
    public final String description;
    RoomMap(String display, String description) {
        this.map = display;
        this.description = description;
    }

}
