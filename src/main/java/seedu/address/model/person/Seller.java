package seedu.address.model.person;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.house.House;
import seedu.address.model.tag.Tag;

/**
 * Represents a seller in the address book.
 */
public class Seller extends Person {
    private final ArrayList<House> houses;

    /**
     * Constructs a new Seller instance.
     *
     * @param name        The name of the seller.
     * @param phone       The phone number of the seller.
     * @param email       The email address of the seller.
     * @param houses      The houses the seller has (modified to accept a list of houses)
     * @param tags        The tags associated with the seller.
     */
    public Seller(Name name, Phone phone, Email email, ArrayList<House> houses, Set<Tag> tags) {
        super(name, phone, email, tags);
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
    public ArrayList<House> getHouse() {
        return this.houses;
    }

    /**
     * Check if the seller's list of houses contains the house
     */
    public boolean hasHouse(House h) {
        return this.houses.contains(h);
    }

    /**
     * Returns the list of houses associated with this seller.
     * @return An ArrayList containing House objects.
     */
    public ArrayList<House> getHouses() {
        return new ArrayList<>(houses);
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
        ArrayList<House> copyHouse = new ArrayList<>();
        Set<Tag> copyTags = new HashSet<>();
        copyTags.add(new Tag("Seller"));

        return new Seller(copyName, copyPhone, copyEmail, copyHouse, copyTags);
    }
}
