package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BLOCK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOUSING_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTALCODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STREET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNITNUMBER;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddSellerCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.house.Block;
import seedu.address.model.house.Condominium;
import seedu.address.model.house.Hdb;
import seedu.address.model.house.House;
import seedu.address.model.house.HousingType;
import seedu.address.model.house.Landed;
import seedu.address.model.house.Level;
import seedu.address.model.house.PostalCode;
import seedu.address.model.house.Price;
import seedu.address.model.house.Street;
import seedu.address.model.house.UnitNumber;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Seller;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddSellerCommand object
 */
public class AddSellerCommandParser implements Parser<AddSellerCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddSellerCommand
     * and returns an AddSellerCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddSellerCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_HOUSING_TYPE, PREFIX_LEVEL, PREFIX_BLOCK, PREFIX_STREET,
                        PREFIX_UNITNUMBER, PREFIX_POSTALCODE, PREFIX_PRICE, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_HOUSING_TYPE,
                PREFIX_POSTALCODE, PREFIX_STREET, PREFIX_UNITNUMBER,
                PREFIX_PRICE) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSellerCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_HOUSING_TYPE, PREFIX_LEVEL,
                PREFIX_EMAIL, PREFIX_BLOCK, PREFIX_STREET, PREFIX_UNITNUMBER, PREFIX_POSTALCODE, PREFIX_PRICE);

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        HousingType housingType = ParserUtil.parseHousing(argMultimap.getValue(PREFIX_HOUSING_TYPE).get());
        Price price = ParserUtil.parsePrice(argMultimap.getValue(PREFIX_PRICE).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        ArrayList<House> houses = new ArrayList<>();
        // Seperated out the methods to create hdb, condominium and landed (adhere to SLAP)
        houses.add(createHouse(argMultimap, housingType, price));

        Seller seller = new Seller(name, phone, email, houses, tagList);
        return new AddSellerCommand(seller);
    }

    private House createHouse(ArgumentMultimap argMultimap, HousingType housingType, Price price)
            throws ParseException {
        PostalCode postalCode = ParserUtil.parsePostalCode(argMultimap.getValue(PREFIX_POSTALCODE).get());
        Street street = ParserUtil.parseStreet(argMultimap.getValue(PREFIX_STREET).get());
        UnitNumber unitNumber = ParserUtil.parseUnitNumber(argMultimap.getValue(PREFIX_UNITNUMBER).get());

        switch (housingType.toString().toLowerCase()) {
        case "hdb":
            return createHdb(argMultimap, postalCode, street, unitNumber, price);
        case "condominium":
            return createCondominium(argMultimap, postalCode, street, unitNumber, price);
        default:
            return new Landed(unitNumber, postalCode, street, price);
        }
    }

    private Hdb createHdb(ArgumentMultimap argMultimap, PostalCode postalCode, Street street,
                          UnitNumber unitNumber, Price price) throws ParseException {
        Block block = ParserUtil.parseBlock(argMultimap.getValue(PREFIX_BLOCK).get());
        Level level = ParserUtil.parseLevel(argMultimap.getValue(PREFIX_LEVEL).get());
        return new Hdb(level, postalCode, street, unitNumber, block, price);
    }

    private Condominium createCondominium(ArgumentMultimap argMultimap, PostalCode postalCode, Street street,
                                          UnitNumber unitNumber, Price price) throws ParseException {
        boolean hasBlock = argMultimap.getValue(PREFIX_BLOCK).isPresent();
        Level level = hasBlock ? ParserUtil.parseLevel(argMultimap.getValue(PREFIX_LEVEL).get()) : null;
        Block block = hasBlock ? ParserUtil.parseBlock(argMultimap.getValue(PREFIX_BLOCK).get()) : null;
        if (block != null && level != null) {
            return new Condominium(level, postalCode, street, unitNumber, block, price);
        } else {
            return new Condominium(level, postalCode, street, unitNumber, price);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
