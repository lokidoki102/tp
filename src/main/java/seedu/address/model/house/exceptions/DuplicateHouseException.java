package seedu.address.model.house.exceptions;

/**
 * Signals that the operation will result in duplicate Houses (Housess are considered duplicates if they have the same
 * identity).
 */
public class DuplicateHouseException extends RuntimeException {
    public DuplicateHouseException() {
        super("Operation would result in duplicate houses");
    }
}
