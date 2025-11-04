package game.room.instances;

import game.room.RoomOutCome;

public class RoomOne  {

    private static final String DESCRIPTION = """
            | You step into a dimly lit room.
                Two strange markings catch your eye on opposite walls
                — one shaped like an “X”, the other like a “?”.
            """;
    public static final String MAP = """
            ======== MAP ========
                     Door
                   /
                 X         ?
                   \\     /
                     You
            =====================""";
    private static final String ENEMY_ENCOUNTER = """
            ╔════════════════════════════════════════╗
            ║                                        ║
            ║    A goblin jumps from the shadows!    ║
            ║    Its rusty blade glints in the       ║
            ║    dim light.                          ║
            ║                                        ║
            ╚════════════════════════════════════════╝
            """;
    private static final String ENEMY_DEFEATED = """
            The goblin collapses with a final shriek.
            You notice it was guarding a small pouch...
            """;
    private static final String TREASURE_FOUND = """
            Behind the '?' marking, you find a small chest.
            
            Inside you discover:
            - 100 gold coins
            - 1x {Item}
            """;

    public static RoomOutCome RoomOne() {
        return null;
    }

    pul

//    @Override
//    protected Item getClassItem(PlayerClass playerClass) {
//        switch (playerClass) {
//            case MAGE -> {
//                return ItemDatabase.WOODEN_STAFF;
//            }
//            case ROGUE -> {
//                return ItemDatabase.IRON_DAGGER;
//            }
//            case WARRIOR -> {
//                return ItemDatabase.IRON_SWORD;
//            }
//            default -> throw new IllegalStateException("Unexpected value: " + playerClass);
//        }
//    }
}
