package model.enums.room;

public enum RoomMap {
    ROOM_ONE("""
            ╚════════ MAP ════════╝
            
                     Door
                   /
                 X         ?
                   \\     /
                     You
            ═══════════════════════""",
            """
                    | You step into a dimly lit room.
                Two strange markings catch your eye on opposite walls
                — one shaped like an “X”, the other like a “?”."""
    ),
    ROOM_TWO("""
            ╚════════ MAP ════════╝
            
                     Door
                      |
                      X

                 X         X

                     You
            
            ═══════════════════════""",
            """
                    | You step into a dim, shadowed chamber.
                Strange “X” markings cover the walls on every side.
                There’s only one path forward — and beyond it, whatever waits forces you to fight."""
    ),
    ROOM_TREE("""
            ╚════════ MAP ════════╝
            
                     Door
            
                 X         ?
            
                     You
            
            ═══════════════════════""",
            """
                    | You step into a shadowed chamber.
                Strange markings catch your eye — an “X” here, a “?” there, scattered across the walls.
                There’s only one path forward, leading straight into whatever waits beyond."""
    ),
    ROOM_FOUR("""
            ╚════════ MAP ════════╝
            
                     Door
            
                      ?
            
                      X

                     You

            ═══════════════════════""",
            """
                    | You step into a vast arena.
                Before you can react, four guards emerge from the shadows, blocking every exit.
                There’s no escape — you must face them all."""
    ),
    ROOM_FIVE("""
            ╚════════ MAP ════════╝
            
                    B O S S
      
                     You
            
            ═══════════════════════""",
            """
                    | You push open the heavy doors into a cavernous chamber.
                The air is thick with tension, shadows twisting unnaturally across the walls."""
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
