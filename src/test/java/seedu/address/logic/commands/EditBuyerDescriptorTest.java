package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BUYER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BUYER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HOUSING_TYPE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SELLER;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditBuyerCommand.EditBuyerDescriptor;
import seedu.address.testutil.EditBuyerDescriptorBuilder;

public class EditBuyerDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditBuyerDescriptor descriptorWithSameValues = new EditBuyerDescriptor(DESC_BUYER_AMY);
        assertTrue(DESC_BUYER_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_BUYER_AMY.equals(DESC_BUYER_AMY));

        // null -> returns false
        assertFalse(DESC_BUYER_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_BUYER_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_BUYER_AMY.equals(DESC_BUYER_BOB));

        // different name -> returns false
        EditBuyerDescriptor editedAmy = new EditBuyerDescriptorBuilder(DESC_BUYER_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_BUYER_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditBuyerDescriptorBuilder(DESC_BUYER_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_BUYER_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditBuyerDescriptorBuilder(DESC_BUYER_AMY).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(DESC_BUYER_AMY.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditBuyerDescriptorBuilder(DESC_BUYER_AMY).withHousingType(VALID_HOUSING_TYPE_BOB).build();
        assertFalse(DESC_BUYER_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditBuyerDescriptorBuilder(DESC_BUYER_AMY).withTags(VALID_TAG_SELLER).build();
        assertFalse(DESC_BUYER_AMY.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        EditBuyerDescriptor editBuyerDescriptor = new EditBuyerDescriptor();
        String expected = EditBuyerDescriptor.class.getCanonicalName()
                + "{name=" + editBuyerDescriptor.getName().orElse(null)
                + ", phone=" + editBuyerDescriptor.getPhone().orElse(null)
                + ", email=" + editBuyerDescriptor.getEmail().orElse(null)
                + ", housingType=" + editBuyerDescriptor.getHousingType().orElse(null)
                + ", budget=" + editBuyerDescriptor.getBudget().orElse(null)
                + ", tags=" + editBuyerDescriptor.getTags().orElse(null) + "}";
        assertEquals(expected, editBuyerDescriptor.toString());
    }
}
