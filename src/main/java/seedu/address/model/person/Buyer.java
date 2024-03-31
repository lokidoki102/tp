package seedu.address.model.person;

import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.house.HousingType;
import seedu.address.model.tag.Tag;

/**
 * Represents a buyer in the address book.
 */
public class Buyer extends Person {

    private final Budget budget;

    /**
     * Constructs a new Buyer instance without specifying a house. Default constructor.
     *
     * @param name        The name of the buyer.
     * @param phone       The phone number of the buyer.
     * @param email       The email address of the buyer.
     * @param housingType The type of housing the buyer wants.
     * @param tags        The tags associated with the buyer.
     * @param budget      The budget of the buyer.
     */
    public Buyer(Name name, Phone phone, Email email, HousingType housingType, Budget budget, Set<Tag> tags) {
        super(name, phone, email, housingType, tags);
        this.budget = budget;
    }

    public Budget getBudget() {
        return budget;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Buyer)) {
            return false;
        }

        if (!super.equals(other)) {
            return false;
        }

        Buyer buyer = (Buyer) other;
        return Objects.equals(budget, buyer.budget);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), budget);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", getName())
                .add("phone", getPhone())
                .add("email", getEmail())
                .add("housingType", getHousingType())
                .add("budget", budget)
                .add("tags", getTags())
                .toString();
    }
}
