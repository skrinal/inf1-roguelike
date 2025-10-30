package game.room.elements;

import game.room.RoomElement;
import game.room.combat.CombatSystem;
import model.Enemy;
import model.Player;
import model.enums.room.RoomResult;

import java.util.Scanner;
import java.util.function.Function;

public class CombatElement extends RoomElement {
    private final Function<Player, Enemy> enemySupplier;
    private final String encounterText;
    private final String victoryText;

    public CombatElement(String description, Function<Player, Enemy> enemySupplier, String encounterText, String victoryText) {
        super(description, true);
        this.enemySupplier = enemySupplier;
        this.encounterText = encounterText;
        this.victoryText = victoryText;
    }

    @Override
    public RoomResult interact(Scanner input, Player player) {
        if (this.isCompleted()) {
            System.out.println("Nothing to do here...");
            return RoomResult.CONTINUE;
        }

        System.out.print(this.encounterText);
        input.nextLine();

        Enemy enemy = this.enemySupplier.apply(player);
        boolean victory = CombatSystem.startCombat(input, player, enemy);
        return null;
    }
}
