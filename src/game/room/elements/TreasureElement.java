package game.room.elements;

import game.room.RoomElement;
import model.Player;
import model.enums.room.RoomResult;

import java.util.Scanner;
import java.util.function.Consumer;

public class TreasureElement extends RoomElement {
    private final Consumer<Player> rewardFunction;
    private final String foundText;

    public TreasureElement(String description,
            Consumer<Player> rewardFunction,
            String foundText) {
        super(description, false);
        this.rewardFunction = rewardFunction;
        this.foundText = foundText;
    }

    @Override
    protected RoomResult interact(Scanner input, Player player) {
        if (this.isCompleted()) {
            System.out.println("Nothing to do here...");
            return RoomResult.CONTINUE;
        }

        System.out.println(this.foundText);
        this.rewardFunction.accept(player);
        this.markCompleted();

        System.out.println("\nPress Enter to continue");
        input.nextLine();

        return RoomResult.CONTINUE;
    }
}
