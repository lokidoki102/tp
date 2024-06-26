package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.AddHouseCommandParser.checkValidity;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BLOCK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOUSING_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTALCODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STREET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNITNUMBER;

import java.util.ArrayList;
import java.util.stream.Stream;

import seedu.address.logic.commands.DeleteHouseCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.house.House;
import seedu.address.model.house.HousingType;
import seedu.address.model.house.PostalCode;
import seedu.address.model.house.Price;
import seedu.address.model.house.Street;
import seedu.address.model.house.UnitNumber;
import seedu.address.model.person.Name;


/**
 * Parses input arguments and creates a new AddHouseCommand object
 */
public class DeleteHouseCommandParser implements Parser<DeleteHouseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteHouseCommand
     * and returns a DeleteHouseCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteHouseCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_HOUSING_TYPE, PREFIX_LEVEL, PREFIX_BLOCK,
                        PREFIX_PRICE, PREFIX_STREET, PREFIX_UNITNUMBER, PREFIX_POSTALCODE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_HOUSING_TYPE, PREFIX_PRICE,
                PREFIX_POSTALCODE, PREFIX_STREET, PREFIX_UNITNUMBER) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteHouseCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_HOUSING_TYPE, PREFIX_LEVEL, PREFIX_PRICE,
                PREFIX_BLOCK, PREFIX_STREET, PREFIX_UNITNUMBER, PREFIX_POSTALCODE);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        HousingType housingType = ParserUtil.parseHousing(argMultimap.getValue(PREFIX_HOUSING_TYPE).get());
        UnitNumber unitNumber = ParserUtil.parseUnitNumber(argMultimap.getValue(PREFIX_UNITNUMBER).get());
        Street street = ParserUtil.parseStreet(argMultimap.getValue(PREFIX_STREET).get());
        PostalCode postalCode = ParserUtil.parsePostalCode(argMultimap.getValue(PREFIX_POSTALCODE).get());
        Price price = ParserUtil.parsePrice(argMultimap.getValue(PREFIX_PRICE).get());

        boolean hasBlock = argMultimap.getValue(PREFIX_BLOCK).isPresent();
        boolean hasLevel = argMultimap.getValue(PREFIX_LEVEL).isPresent();

        ArrayList<House> houses = new ArrayList<House>();
        House house = checkValidity(housingType, unitNumber, street, postalCode, price, hasBlock, hasLevel,
                argMultimap);
        houses.add(house);

        return new DeleteHouseCommand(house, name);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
