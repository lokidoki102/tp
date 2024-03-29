package seedu.address.model.house;

import java.util.ArrayList;
import java.util.Arrays;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;


/**
 * Represents a House's housing type.
 * Guarantees: immutable; is valid as declared in {@link #isValidLevel(String)}
 */
public class HousingType {

    public static final String MESSAGE_CONSTRAINTS =
            "HousingType should only be Landed or NonLanded";
    public static final ArrayList<String> VALIDATION_REGEX = new
            ArrayList<>(Arrays.asList("condominium", "hdb", "landed"));

    public final String value;

    /**
     * Constructs a {@code HousingType}.
     *
     * @param housingType A valid housing type.
     */
    public HousingType(String housingType) {
        requireNonNull(housingType);
        checkArgument(isValidLevel(housingType), MESSAGE_CONSTRAINTS);
        value = housingType;
    }

    /**
     * Returns true if a given string is a valid housing type.
     */
    public static boolean isValidLevel(String test) {
        return VALIDATION_REGEX.contains(test.toLowerCase());
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof HousingType)) {
            return false;
        }

        HousingType otherHousingType = (HousingType) other;
        return value.equals(otherHousingType.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
