package seedu.address.model.house.exceptions;

/**
 * Signals that the house is not found.
 */
public class HouseNotFoundException extends RuntimeException {
    public HouseNotFoundException() {
        super("House not found!");
    }
}
