package game.room;

import model.enums.room.RoomResult;
import model.enums.room.RoomType;

public record RoomOutCome(RoomResult result, RoomType nextRoom) {


}
