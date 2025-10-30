package game.room;

import model.Player;
import model.enums.room.RoomResult;

import java.util.Scanner;

public abstract class RoomElement {
    private final String description;
    private final boolean required;
    private boolean completed;

    protected RoomElement(String description, boolean required) {
        this.description = description;
        this.required = required;
        this.completed = false;
    }

    protected abstract RoomResult interact(Scanner input, Player player);

    protected String getDescription() {
        return this.description;
    }

    protected boolean isCompleted() {
        return this.completed;
    }

    protected boolean isRequired() {
        return this.required;
    }

    protected void markCompleted() {
        this.completed = true;
    }
}
