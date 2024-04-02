package seedu.address.model.house;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class HousingTypeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new HousingType(null));
    }

    @Test
    public void constructor_invalidHousingType_throwsIllegalArgumentException() {
        String invalidHousingType = "bungalow";
        assertThrows(IllegalArgumentException.class, () -> new HousingType(invalidHousingType));
    }

    @Test
    public void isValidHousingType() {
        // null housing type
        assertThrows(NullPointerException.class, () -> HousingType.isValidHousingType(null));

        // invalid housing types
        assertFalse(HousingType.isValidHousingType("bungalow")); // Invalid types
        assertFalse(HousingType.isValidHousingType("")); // Empty string
        assertFalse(HousingType.isValidHousingType(" ")); // Spaces only

        // valid housing types
        assertTrue(HousingType.isValidHousingType("condominium")); // Exact match
        assertTrue(HousingType.isValidHousingType("Condominium")); // Case insensitive
        assertTrue(HousingType.isValidHousingType("hdb")); // Exact match
        assertTrue(HousingType.isValidHousingType("HDB")); // Case insensitive
        assertTrue(HousingType.isValidHousingType("landed")); // Exact match
        assertTrue(HousingType.isValidHousingType("Landed")); // Case insensitive
    }

    @Test
    public void equals() {
        HousingType housingType = new HousingType("condominium");

        // same values -> returns true
        assertTrue(housingType.equals(new HousingType("condominium")));

        // same object -> returns true
        assertTrue(housingType.equals(housingType));

        // null -> returns false
        assertFalse(housingType.equals(null));

        // different types -> returns false
        assertFalse(housingType.equals(5));

        // different values -> returns false
        assertFalse(housingType.equals(new HousingType("hdb")));
    }

    @Test
    public void hashCodeTest() {
        HousingType housingType1 = new HousingType("condominium");
        HousingType housingType2 = new HousingType("condominium");
        HousingType housingType3 = new HousingType("hdb");

        assertEquals(housingType1.hashCode(), housingType2.hashCode());

        assertFalse(housingType1.hashCode() == housingType3.hashCode());
    }
}
