package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.BLOCK_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.HOUSING_TYPE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.LEVEL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.POSTAL_CODE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.STREET_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_SELLER;
import static seedu.address.logic.commands.CommandTestUtil.UNIT_NUMBER_DESC_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalHouses.HOUSE2;
import static seedu.address.testutil.TypicalPersons.AMY_SELLER;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddSellerCommand;
import seedu.address.model.person.Seller;
import seedu.address.testutil.SellerBuilder;

public class AddSellerCommandParserTest {

    private AddSellerCommandParser parser = new AddSellerCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Seller expectedSeller = new SellerBuilder(AMY_SELLER).withHouses(HOUSE2).build();

        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + HOUSING_TYPE_DESC_AMY + STREET_DESC_AMY + BLOCK_DESC_AMY
                + LEVEL_DESC_AMY + UNIT_NUMBER_DESC_AMY + POSTAL_CODE_DESC_AMY
                + PRICE_DESC_AMY , new AddSellerCommand(expectedSeller));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        Seller expectedSeller = new SellerBuilder(AMY_SELLER).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + HOUSING_TYPE_DESC_AMY + STREET_DESC_AMY + BLOCK_DESC_AMY
                + LEVEL_DESC_AMY + UNIT_NUMBER_DESC_AMY + POSTAL_CODE_DESC_AMY
                + PRICE_DESC_AMY, new AddSellerCommand(expectedSeller));
    }

    @Test
    public void parse_missingNamePrefix_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSellerCommand.MESSAGE_USAGE);

        String commandWithMissingName = PHONE_DESC_AMY + EMAIL_DESC_AMY
                + HOUSING_TYPE_DESC_AMY + STREET_DESC_AMY + BLOCK_DESC_AMY
                + LEVEL_DESC_AMY + UNIT_NUMBER_DESC_AMY + POSTAL_CODE_DESC_AMY
                + PRICE_DESC_AMY + TAG_DESC_SELLER;

        assertParseFailure(parser, commandWithMissingName, expectedMessage);
    }

    @Test
    public void parse_missingPhonePrefix_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSellerCommand.MESSAGE_USAGE);

        String commandWithMissingPhone = NAME_DESC_AMY + EMAIL_DESC_AMY
                + HOUSING_TYPE_DESC_AMY + STREET_DESC_AMY + BLOCK_DESC_AMY
                + LEVEL_DESC_AMY + UNIT_NUMBER_DESC_AMY + POSTAL_CODE_DESC_AMY
                + PRICE_DESC_AMY + TAG_DESC_SELLER;

        assertParseFailure(parser, commandWithMissingPhone, expectedMessage);
    }

    @Test
    public void parse_missingEmailPrefix_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSellerCommand.MESSAGE_USAGE);

        String commandWithMissingEmail = NAME_DESC_AMY + PHONE_DESC_AMY
                + HOUSING_TYPE_DESC_AMY + STREET_DESC_AMY + BLOCK_DESC_AMY
                + LEVEL_DESC_AMY + UNIT_NUMBER_DESC_AMY + POSTAL_CODE_DESC_AMY
                + PRICE_DESC_AMY + TAG_DESC_SELLER;

        assertParseFailure(parser, commandWithMissingEmail, expectedMessage);
    }

    @Test
    public void parse_missingHousingTypePrefix_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSellerCommand.MESSAGE_USAGE);

        String commandWithMissingHousingType = NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + STREET_DESC_AMY + BLOCK_DESC_AMY
                + LEVEL_DESC_AMY + UNIT_NUMBER_DESC_AMY + POSTAL_CODE_DESC_AMY
                + PRICE_DESC_AMY + TAG_DESC_SELLER;

        assertParseFailure(parser, commandWithMissingHousingType, expectedMessage);
    }

    @Test
    public void parse_missingPostalCodePrefix_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSellerCommand.MESSAGE_USAGE);

        String commandWithMissingPostalCode = NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + HOUSING_TYPE_DESC_AMY + STREET_DESC_AMY + BLOCK_DESC_AMY
                + LEVEL_DESC_AMY + UNIT_NUMBER_DESC_AMY
                + PRICE_DESC_AMY + TAG_DESC_SELLER;

        assertParseFailure(parser, commandWithMissingPostalCode, expectedMessage);
    }

    @Test
    public void parse_missingStreetPrefix_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSellerCommand.MESSAGE_USAGE);

        String commandWithMissingStreet = NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + HOUSING_TYPE_DESC_AMY + BLOCK_DESC_AMY
                + LEVEL_DESC_AMY + UNIT_NUMBER_DESC_AMY + POSTAL_CODE_DESC_AMY
                + PRICE_DESC_AMY + TAG_DESC_SELLER;

        assertParseFailure(parser, commandWithMissingStreet, expectedMessage);
    }

    @Test
    public void parse_missingUnitNumberPrefix_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSellerCommand.MESSAGE_USAGE);

        String commandWithMissingUnitNumber = NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + HOUSING_TYPE_DESC_AMY + STREET_DESC_AMY + BLOCK_DESC_AMY
                + LEVEL_DESC_AMY + POSTAL_CODE_DESC_AMY
                + PRICE_DESC_AMY + TAG_DESC_SELLER;

        assertParseFailure(parser, commandWithMissingUnitNumber, expectedMessage);
    }

    @Test
    public void parse_missingPricePrefix_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSellerCommand.MESSAGE_USAGE);

        String commandWithMissingPrice = NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + HOUSING_TYPE_DESC_AMY + STREET_DESC_AMY + BLOCK_DESC_AMY
                + LEVEL_DESC_AMY + UNIT_NUMBER_DESC_AMY + POSTAL_CODE_DESC_AMY
                + TAG_DESC_SELLER;

        assertParseFailure(parser, commandWithMissingPrice, expectedMessage);
    }
}
