package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BUYER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BUYER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditBuyerCommand.EditBuyerDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Buyer;
import seedu.address.testutil.BuyerBuilder;
import seedu.address.testutil.EditBuyerDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditBuyerCommand.
 */
public class EditBuyerCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Index indexLastBuyer = Index.fromOneBased(model.getFilteredPersonList().size());
    private final Index indexSecondLastBuyer = Index.fromOneBased(model.getFilteredPersonList().size() - 1);


    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Buyer editedBuyer = new BuyerBuilder().build();
        EditBuyerDescriptor descriptor = new EditBuyerDescriptorBuilder(editedBuyer).build();
        EditBuyerCommand editBuyerCommand = new EditBuyerCommand(indexLastBuyer, descriptor);

        String expectedMessage = String.format(EditBuyerCommand.MESSAGE_EDIT_BUYER_SUCCESS,
                Messages.format(editedBuyer));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(indexLastBuyer.getZeroBased()), editedBuyer);

        assertCommandSuccess(editBuyerCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_notBuyerType_failure() {
        Buyer editedBuyer = new BuyerBuilder().build();
        EditBuyerDescriptor descriptor = new EditBuyerDescriptorBuilder(editedBuyer).build();
        EditBuyerCommand editBuyerCommand = new EditBuyerCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditBuyerCommand.MESSAGE_WRONG_TYPE,
                Messages.format(editedBuyer));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(indexLastBuyer.getZeroBased()), editedBuyer);

        assertCommandFailure(editBuyerCommand, model, expectedMessage);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastBuyer = Index.fromOneBased(model.getFilteredPersonList().size());
        // In this dummy model, buyers are guaranteed to be at the end of the list.
        Buyer lastBuyer = (Buyer) model.getFilteredPersonList().get(indexLastBuyer.getZeroBased());

        BuyerBuilder buyerInList = new BuyerBuilder(lastBuyer);
        Buyer editedBuyer = buyerInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB).build();

        EditBuyerDescriptor descriptor = new EditBuyerDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).build();
        EditBuyerCommand editBuyerCommand = new EditBuyerCommand(indexLastBuyer, descriptor);

        String expectedMessage = String.format(EditBuyerCommand.MESSAGE_EDIT_BUYER_SUCCESS,
                Messages.format(editedBuyer));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(lastBuyer, editedBuyer);

        assertCommandSuccess(editBuyerCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditBuyerCommand editBuyerCommand = new EditBuyerCommand(indexLastBuyer, new EditBuyerDescriptor());
        Buyer editedBuyer = (Buyer) model.getFilteredPersonList().get(indexLastBuyer.getZeroBased());

        String expectedMessage = String.format(EditBuyerCommand.MESSAGE_EDIT_BUYER_SUCCESS,
                Messages.format(editedBuyer));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editBuyerCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, indexLastBuyer);
        Buyer buyerInFilteredList = (Buyer) model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Buyer editedBuyer = new BuyerBuilder(buyerInFilteredList).withName(VALID_NAME_BOB).build();
        EditBuyerCommand editBuyerCommand = new EditBuyerCommand(INDEX_FIRST_PERSON,
                new EditBuyerDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditBuyerCommand.MESSAGE_EDIT_BUYER_SUCCESS,
                Messages.format(editedBuyer));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedBuyer);

        assertCommandSuccess(editBuyerCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Buyer lastBuyer = (Buyer) model.getFilteredPersonList().get(indexLastBuyer.getZeroBased());
        EditBuyerDescriptor descriptor = new EditBuyerDescriptorBuilder(lastBuyer).build();
        EditBuyerCommand editBuyerCommand = new EditBuyerCommand(indexSecondLastBuyer, descriptor);

        assertCommandFailure(editBuyerCommand, model, EditBuyerCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, indexLastBuyer);

        Buyer buyerInList = (Buyer) model.getAddressBook().getPersonList().get(indexSecondLastBuyer.getZeroBased());
        EditBuyerCommand editBuyerCommand = new EditBuyerCommand(INDEX_FIRST_PERSON,
                new EditBuyerDescriptorBuilder(buyerInList).build());

        assertCommandFailure(editBuyerCommand, model, EditBuyerCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditBuyerDescriptor descriptor = new EditBuyerDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditBuyerCommand editBuyerCommand = new EditBuyerCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editBuyerCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of EstateEase
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, indexLastBuyer);
        Index outOfBoundIndex = indexSecondLastBuyer;

        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        EditBuyerCommand editBuyerCommand = new EditBuyerCommand(outOfBoundIndex,
                new EditBuyerDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editBuyerCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditBuyerCommand standardCommand = new EditBuyerCommand(indexLastBuyer, DESC_BUYER_AMY);

        // same values -> returns true
        EditBuyerDescriptor copyDescriptor = new EditBuyerDescriptor(DESC_BUYER_AMY);
        EditBuyerCommand commandWithSameValues = new EditBuyerCommand(indexLastBuyer, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditBuyerCommand(indexSecondLastBuyer, DESC_BUYER_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditBuyerCommand(indexLastBuyer, DESC_BUYER_BOB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditBuyerDescriptor editBuyerDescriptor = new EditBuyerDescriptor();
        EditBuyerCommand editBuyerCommand = new EditBuyerCommand(index, editBuyerDescriptor);
        String expected = EditBuyerCommand.class.getCanonicalName() + "{index=" + index + ", editBuyerDescriptor="
                + editBuyerDescriptor + "}";
        assertEquals(expected, editBuyerCommand.toString());
    }

}
