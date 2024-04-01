package seedu.address.model.house.exceptions;

/**
 * Signals that the operation will result in duplicate Houses (Housess are considered duplicates if they have the same
 * identity).
 */
public class HouseNotFoundException extends RuntimeException {
    public HouseNotFoundException() {
        super("House not found!");
    }
}
