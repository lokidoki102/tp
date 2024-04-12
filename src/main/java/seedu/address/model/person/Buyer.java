package seedu.address.model.person;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.house.HousingType;

/**
 * Represents a buyer in EstateEase.
 */
public class Buyer extends Person {

    private final Budget budget;
    private final HousingType preferredHousingType;

    /**
     * Constructs a new Buyer instance without specifying a house. Default constructor.
     *
     * @param name        The name of the buyer.
     * @param phone       The phone number of the buyer.
     * @param email       The email address of the buyer.
     * @param preferredHousingType The type of housing the buyer wants.
     * @param budget      The budget of the buyer.
     */
    public Buyer(Name name, Phone phone, Email email, Budget budget, HousingType preferredHousingType) {
        super(name, phone, email);
        this.preferredHousingType = preferredHousingType;
        this.budget = budget;
    }

    public Budget getBudget() {
        return budget;
    }

    public HousingType getPreferredHousingType() {
        return preferredHousingType;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Buyer)) {
            return false;
        }

        if (!super.equals(other)) {
            return false;
        }

        Buyer buyer = (Buyer) other;
        return Objects.equals(budget, buyer.budget)
                && Objects.equals(preferredHousingType, buyer.preferredHousingType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), budget, preferredHousingType);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", getName())
                .add("phone", getPhone())
                .add("email", getEmail())
                .add("housingType", preferredHousingType)
                .add("budget", budget)
                .add("tags", getTags())
                .toString();
    }
}
