package game.room;

import com.sun.jdi.ClassType;
import model.Item;
import model.Player;
import model.enums.PlayerClass;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Room {
    private final String description;
    private final String mapArt;
    private final Map<String, RoomElement> elements;

    public Room(String description, String mapArt) {
        this.description = description;
        this.mapArt = mapArt;
        this.elements = new LinkedHashMap<>();
    }

    protected void addElement(String key, RoomElement element) {
        this.elements.put(key, element);
    }

    public String getDescription() {
        return this.description;
    }
    public String getMapArt() {
        return this.mapArt;
    }

    protected abstract Item getClassItem(PlayerClass playerClass);

}
