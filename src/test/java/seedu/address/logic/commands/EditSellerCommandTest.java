package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_SELLER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_SELLER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SELLER;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditSellerCommand.EditSellerDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Seller;
import seedu.address.testutil.EditSellerDescriptorBuilder;
import seedu.address.testutil.SellerBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditSellerCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Seller editedSeller = new SellerBuilder().build();
        EditSellerDescriptor descriptor = new EditSellerDescriptorBuilder(editedSeller).build();
        EditSellerCommand editCommand = new EditSellerCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditSellerCommand.MESSAGE_EDIT_SELLER_SUCCESS,
            Messages.format(editedSeller));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedSeller);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_notSellerType_failure() {
        Seller editedSeller = new SellerBuilder().build();
        EditSellerDescriptor descriptor = new EditSellerDescriptorBuilder(editedSeller).build();
        // INDEX_SECOND_PERSON: second person in the dummy is a buyer
        EditSellerCommand editSellerCommand = new EditSellerCommand(INDEX_SECOND_PERSON, descriptor);

        String expectedMessage = String.format(EditSellerCommand.MESSAGE_WRONG_TYPE,
                Messages.format(editedSeller));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), editedSeller);
        assertCommandFailure(editSellerCommand, model, expectedMessage);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        // In this dummy model, sellers are guaranteed to be at the start of the list.
        Seller firstSeller = (Seller) model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        SellerBuilder sellerInList = new SellerBuilder(firstSeller);
        Seller editedSeller = sellerInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_SELLER).build();

        EditSellerDescriptor descriptor = new EditSellerDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_SELLER).build();
        EditSellerCommand editSellerCommand = new EditSellerCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditSellerCommand.MESSAGE_EDIT_SELLER_SUCCESS,
            Messages.format(editedSeller));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstSeller, editedSeller);

        assertCommandSuccess(editSellerCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditSellerCommand editSellerCommand = new EditSellerCommand(INDEX_FIRST_PERSON, new EditSellerDescriptor());
        // Safe to cast because sellers are guaranteed to be at the start of the list.
        Seller editedSeller = (Seller) model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditSellerCommand.MESSAGE_EDIT_SELLER_SUCCESS,
            Messages.format(editedSeller));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editSellerCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        // Safe to cast because sellers are guaranteed to be at the start of the list.
        Seller personInFilteredList = (Seller) model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Seller editedPerson = new SellerBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
        EditSellerCommand editSellerCommand = new EditSellerCommand(INDEX_FIRST_PERSON,
                new EditSellerDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditSellerCommand.MESSAGE_EDIT_SELLER_SUCCESS,
            Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(editSellerCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        // Safe to cast because sellers are guaranteed to be at the start of the list.
        Seller firstPerson = (Seller) model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditSellerDescriptor descriptor = new EditSellerDescriptorBuilder(firstPerson).build();
        EditSellerCommand editCommand = new EditSellerCommand(INDEX_THIRD_PERSON, descriptor);

        assertCommandFailure(editCommand, model, EditSellerCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Seller personInList = (Seller) model.getAddressBook().getPersonList().get(INDEX_THIRD_PERSON.getZeroBased());
        EditSellerCommand editCommand = new EditSellerCommand(INDEX_FIRST_PERSON,
                new EditSellerDescriptorBuilder(personInList).build());

        assertCommandFailure(editCommand, model, EditSellerCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditSellerDescriptor descriptor = new EditSellerDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditSellerCommand editCommand = new EditSellerCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of EstateEase
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        EditSellerCommand editCommand = new EditSellerCommand(outOfBoundIndex,
                new EditSellerDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditSellerCommand standardCommand = new EditSellerCommand(INDEX_FIRST_PERSON, DESC_SELLER_AMY);

        // same values -> returns true
        EditSellerDescriptor copyDescriptor = new EditSellerDescriptor(DESC_SELLER_AMY);
        EditSellerCommand commandWithSameValues = new EditSellerCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditSellerCommand(INDEX_SECOND_PERSON, DESC_SELLER_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditSellerCommand(INDEX_FIRST_PERSON, DESC_SELLER_BOB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditSellerDescriptor editSellerDescriptor = new EditSellerDescriptor();
        EditSellerCommand editCommand = new EditSellerCommand(index, editSellerDescriptor);
        String expected = EditSellerCommand.class.getCanonicalName() + "{index=" + index + ", editSellerDescriptor="
                + editSellerDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
