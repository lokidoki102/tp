package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_SELLER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_SELLER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BUYER;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditSellerCommand.EditSellerDescriptor;
import seedu.address.testutil.EditSellerDescriptorBuilder;

public class EditSellerDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditSellerDescriptor descriptorWithSameValues = new EditSellerDescriptor(DESC_SELLER_AMY);
        assertTrue(DESC_SELLER_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_SELLER_AMY.equals(DESC_SELLER_AMY));

        // null -> returns false
        assertFalse(DESC_SELLER_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_SELLER_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_SELLER_AMY.equals(DESC_SELLER_BOB));

        // different name -> returns false
        EditSellerDescriptor editedAmy = new EditSellerDescriptorBuilder(DESC_SELLER_AMY)
                .withName(VALID_NAME_BOB).build();
        assertFalse(DESC_SELLER_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditSellerDescriptorBuilder(DESC_SELLER_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_SELLER_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditSellerDescriptorBuilder(DESC_SELLER_AMY).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(DESC_SELLER_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditSellerDescriptorBuilder(DESC_SELLER_AMY).withTags(VALID_TAG_BUYER).build();
        assertFalse(DESC_SELLER_AMY.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        EditSellerDescriptor editSellerDescriptor = new EditSellerDescriptor();
        String expected = EditSellerDescriptor.class.getCanonicalName()
                + "{name=" + editSellerDescriptor.getName().orElse(null)
                + ", phone=" + editSellerDescriptor.getPhone().orElse(null)
                + ", email=" + editSellerDescriptor.getEmail().orElse(null)
                + ", tags=" + editSellerDescriptor.getTags().orElse(null) + "}";
        assertEquals(expected, editSellerDescriptor.toString());
    }
}
