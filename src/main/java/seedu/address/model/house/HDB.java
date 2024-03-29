package seedu.address.model.house;

import seedu.address.commons.util.ToStringBuilder;


/**
 * Represents a HDB.
 */
class HDB implements House {

    public final PostalCode postalCode;
    public final Street street;
    public final UnitNumber unitNumber;
    public final Block block;
    public final Level level;

    /**
     * Constructs a HDB.
     *
     * @param unitNumber The unit number of the house.
     * @param postalCode The postal code of the house.
     * @param street     The street of the house.
     * @param block      The block of the house.
     * @param level      The level of the house.
     */
    public HDB(UnitNumber unitNumber, PostalCode postalCode, Street street, Block block, Level level) {
        this.postalCode = postalCode;
        this.street = street;
        this.unitNumber = unitNumber;
        this.block = block;
        this.level = level;
    }

    @Override
    public UnitNumber getUnitNumber() {
        return this.unitNumber;
    }

    @Override
    public PostalCode getPostalCode() {
        return this.postalCode;
    }

    @Override
    public Street getStreet() {
        return this.street;
    }

    /**
     * Checks if this house is equal to another object.
     *
     * @param other The object to compare to.
     * @return True if the objects are equal, false otherwise.
     */

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof House)) {
            return false;
        }

        House otherStreet = (House) other;
        return this.toString().equals(otherStreet.toString());
    }

    /**
     * Returns a string representation of the landed house.
     *
     * @return A string representation of the landed house.
     */
    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this);
        // For now, it just appends the super class's toString method.
        return "Landed House: " + builder.toString() + ", " + new ToStringBuilder(this)
                .add("Unit Number", unitNumber)
                .add("Street", street)
                .add("Postal Code", postalCode)
                .toString();
    }
}

}

