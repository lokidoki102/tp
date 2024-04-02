package seedu.address.model.person.exceptions;

/**
 * Signals that the Person is not a Seller.
 */
public class InvalidSellerException extends RuntimeException {
    public InvalidSellerException() {
        super("Person must be a Seller to add a house");
    }
}
