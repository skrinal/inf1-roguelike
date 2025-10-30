package game.room.instances;

import com.sun.jdi.ClassType;
import data.ItemDatabase;
import game.room.Room;
import game.room.elements.CombatElement;
import game.room.elements.TreasureElement;
import model.Item;
import model.enemies.SkeletonWarrior;
import model.enums.PlayerClass;

import static model.enums.PlayerClass.*;

public class RoomOne extends Room {

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

    public RoomOne() {
        super(DESCRIPTION, MAP);

        addElement("enemy", new CombatElement(
                "Investigate 'X' marking",
                player -> new SkeletonWarrior(
                        "Vlado Ice", 100, player.getLevel()),
                ENEMY_ENCOUNTER,
                ENEMY_DEFEATED
        ));

        addElement("treasure", new TreasureElement(
                "Investigate 'X' marking",
                player -> {
                    player.addGold(100);
                    Item newItem = getClassItem(player.getClassType());
//                    Item newItem = switch (player.getClassType()) {
//                        case MAGE -> ItemDatabase.WOODEN_STAFF;
//                        case ROGUE -> ItemDatabase.IRON_DAGGER;
//                        case WARRIOR -> ItemDatabase.IRON_SWORD;
//                    };
                    player.addItem(newItem);
                    //TREASURE_FOUND.replace("{Item}", newItem.toString());
                },
                player -> TREASURE_FOUND.replace("{Item}", )
                //TREASURE_FOUND
        ));

    }

    @Override
    protected Item getClassItem(PlayerClass playerClass) {
        switch (playerClass) {
            case MAGE -> {
                return ItemDatabase.WOODEN_STAFF;
            }
            case ROGUE -> {
                return ItemDatabase.IRON_DAGGER;
            }
            case WARRIOR -> {
                return ItemDatabase.IRON_SWORD;
            }
        };
    }
}
