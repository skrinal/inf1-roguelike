package model.enums.room;

/**
 * An enum representing different types of rooms in a system.
 * Each type corresponds to a specific level.
 */
public enum RoomType {
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE;

    /**
     * To be honest, I didn't think of this method until I post on stackOverflow.
     * I didn't know something like ordinal() existed, but now I get it.
     * - https://stackoverflow.com/questions/34159413/java-get-next-enum-value-or-start-from-first
     *
     * Method returns next room type or null if it's the last room.
     */
    public RoomType next() {
        int nextOrdinal = this.ordinal() + 1;
        RoomType[] values = RoomType.values();

        return (nextOrdinal < values.length) ? values[nextOrdinal] : null;
    }
}
