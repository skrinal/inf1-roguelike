package utility;

public class iba {

    private final String DESCRIPTION = """
            | You step into a dimly lit room.
                Two strange markings catch your eye on opposite walls
                — one shaped like an “X”, the other like a “?”.
            """;
    public final String MAP = """
            ======== MAP ========
                     Door
                   /
                 X         ?
                   \\     /
                     You
            =====================""";
    private final String ENEMY_ENCOUNTER = """
            ╔════════════════════════════════════════╗
            ║                                        ║
            ║    A goblin jumps from the shadows!    ║
            ║    Its rusty blade glints in the       ║
            ║    dim light.                          ║
            ║                                        ║
            ╚════════════════════════════════════════╝
            """;
    private final String ENEMY_DEFEATED = """
            The goblin collapses with a final shriek.
            You notice it was guarding a small pouch...
            """;
    private final String TREASURE_FOUND = """
            Behind the '?' marking, you find a small chest.
            
            Inside you discover:
            - 100 gold coins
            - 1x {Item}
            """;
}
