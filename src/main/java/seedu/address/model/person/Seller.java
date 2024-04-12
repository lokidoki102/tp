package seedu.address.model.person;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.house.House;

/**
 * Represents a seller in EstateEase.
 */
public class Seller extends Person {
    private final ObservableList<House> houses;

    /**
     * Constructs a new Seller instance.
     *
     * @param name        The name of the seller.
     * @param phone       The phone number of the seller.
     * @param email       The email address of the seller.
     * @param houses      The houses the seller has (modified to accept a list of houses)
     */
    public Seller(Name name, Phone phone, Email email, ObservableList<House> houses) {
        super(name, phone, email);
        this.houses = houses;
    }

    /**
     * Adds a new house to the seller's list of houses.
     * @param house The new house to add.
     */
    public void addHouse(House house) {
        this.houses.add(house);
    }

    /**
     * Removes a house from the seller's list of houses.
     * @param house The house to remove.
     */
    public void removeHouse(House house) {
        this.houses.remove(house);
    }

    /**
     * Gets the seller's list of houses.
     */
    public ObservableList<House> getHouses() {
        return this.houses;
    }

    /**
     * Check if the seller's list of houses contains the house
     */
    public boolean hasHouse(House h) {
        return this.houses.contains(h);
    }

    /**
     * Creates a copy of seller.
     *
     * @return An instance of seller.
     */
    public Seller copy() {
        Name copyName = new Name(this.getName().toString());
        Phone copyPhone = new Phone(this.getPhone().toString());
        Email copyEmail = new Email(this.getEmail().toString());
        ObservableList<House> copyHouse = FXCollections.observableArrayList();

        return new Seller(copyName, copyPhone, copyEmail, copyHouse);
    }
}
