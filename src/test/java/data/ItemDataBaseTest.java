package data;

import model.Player;
import model.Item;
import model.players.Mage;
import model.players.Rogue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class ItemDataBaseTest {

    private Player magePlayer;
    private Player roguePlayer;

    @BeforeEach
    void setUp() {
        this.magePlayer = new Mage("Gandalfos");
        this.roguePlayer = new Rogue("Rogues");
    }

    @Test
    void testSameInstanceForDifferentPlayers() {
        ItemDatabase db1 = ItemDatabase.getInstance(this.magePlayer);
        ItemDatabase db2 = ItemDatabase.getInstance(this.roguePlayer);

        assertSame(db1, db2, "ItemDatabase should be a singleton");
    }

    @Test
    void testGetRandomItem() {
        ItemDatabase db = ItemDatabase.getInstance(this.magePlayer);
        Item item = db.getRandomItem();

        assertNotNull(item, "getRandomItem should return an item");
        assertNotNull(item.getType(), "Item should have a type");
    }

}
