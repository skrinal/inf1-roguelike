package game.room;

import model.enums.room.RoomResult;
import model.enums.room.RoomType;

/**
 * Represents the outcome of a player's interaction within a specific room in the game.
 * This record stores the outcome of the room and which room the player should go to next.
 */
public record RoomOutCome(RoomResult result, RoomType nextRoom) {

}
