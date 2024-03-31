package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_BLOCK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOUSING_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTALCODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STREET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNITNUMBER;

import java.util.Set;

import seedu.address.logic.commands.AddBuyerCommand;
import seedu.address.logic.commands.AddSellerCommand;
import seedu.address.logic.commands.EditBuyerCommand.EditBuyerDescriptor;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.house.Condominium;
import seedu.address.model.house.Hdb;
import seedu.address.model.house.House;
import seedu.address.model.person.Buyer;
import seedu.address.model.person.Person;
import seedu.address.model.person.Seller;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code buyer}.
     */
    public static String getAddBuyerCommand(Buyer buyer) {
        return AddBuyerCommand.COMMAND_WORD + " " + getBuyerDetails(buyer);
    }

    /**
     * Returns an add seller command string for adding the {@code seller}.
     */
    public static String getAddSellerCommand(Seller seller) {
        return AddSellerCommand.COMMAND_WORD + " " + getSellerDetails(seller);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        sb.append(PREFIX_PHONE + person.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + person.getEmail().value + " ");
        sb.append(PREFIX_HOUSING_TYPE + person.getHousingType().value + " ");
        person.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code buyer}'s details.
     */
    public static String getBuyerDetails(Buyer buyer) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + buyer.getName().fullName + " ");
        sb.append(PREFIX_PHONE + buyer.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + buyer.getEmail().value + " ");
        sb.append(PREFIX_HOUSING_TYPE + buyer.getHousingType().value + " ");
        sb.append(PREFIX_BUDGET + buyer.getBudget().value + " ");
        buyer.getTags().stream().forEach(
                s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of the command string for the given {@code seller}'s details, including houses.
     */
    public static String getSellerDetails(Seller seller) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + seller.getName().fullName + " ");
        sb.append(PREFIX_PHONE + seller.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + seller.getEmail().value + " ");
        sb.append(PREFIX_HOUSING_TYPE + seller.getHousingType().value + " ");
        // Append house details
        for (House house : seller.getHouses()) {
            sb.append(PREFIX_STREET + house.getStreet().value + " ");
            if (house instanceof Hdb) {
                Hdb hdb = (Hdb) house;
                if (hdb.getBlock() != null) {
                    sb.append(PREFIX_BLOCK).append(hdb.getBlock().value).append(" ");
                }
                if (hdb.getLevel() != null) {
                    sb.append(PREFIX_LEVEL).append(hdb.getLevel().value).append(" ");
                }
            } else if (house instanceof Condominium) {
                Condominium condominium = (Condominium) house;
                if (condominium.getBlock() != null) {
                    sb.append(PREFIX_BLOCK).append(condominium.getBlock().value).append(" ");
                }
                if (condominium.getLevel() != null) {
                    sb.append(PREFIX_LEVEL).append(condominium.getLevel().value).append(" ");
                }
            }
            sb.append(PREFIX_UNITNUMBER + house.getUnitNumber().value + " ");
            sb.append(PREFIX_POSTALCODE + house.getPostalCode().value + " ");

        }
        seller.getTags().stream().forEach(
                s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString().trim();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getHousingType().ifPresent(housingType -> sb.append(PREFIX_HOUSING_TYPE).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditBuyerDescriptor}'s details.
     */
    public static String getEditBuyerDescriptorDetails(EditBuyerDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getHousingType().ifPresent(housingType ->
                sb.append(PREFIX_HOUSING_TYPE).append(housingType).append(" "));
        descriptor.getBudget().ifPresent(budget -> sb.append(PREFIX_BUDGET).append(budget.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
