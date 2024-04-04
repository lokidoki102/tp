package seedu.address.storage;

import static seedu.address.logic.commands.AddHouseCommand.MESSAGE_DUPLICATE_HOUSE;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.house.House;
import seedu.address.model.person.Buyer;
import seedu.address.model.person.Seller;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {
    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    private final List<JsonAdaptedSeller> sellers = new ArrayList<>();
    private final List<JsonAdaptedBuyer> buyers = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("buyers") List<JsonAdaptedBuyer> buyers,
                @JsonProperty("sellers") List<JsonAdaptedSeller> sellers) {
        if (buyers != null) {
            this.buyers.addAll(buyers);
        }
        if (sellers != null) {
            this.sellers.addAll(sellers);
        }
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        source.getPersonList().stream()
                .filter(Seller.class::isInstance)
                .map(Seller.class::cast)
                .map(JsonAdaptedSeller::new)
                .forEach(sellers::add);

        source.getPersonList().stream()
                .filter(Buyer.class::isInstance)
                .map(Buyer.class::cast)
                .map(JsonAdaptedBuyer::new)
                .forEach(buyers::add);
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();

        for (JsonAdaptedSeller jsonAdaptedSeller : sellers) {
            Seller seller = jsonAdaptedSeller.toModelType();
            for (House house : seller.getHouses()) {
                if (addressBook.hasHouse(house)) {
                    throw new IllegalValueException(MESSAGE_DUPLICATE_HOUSE);
                }
                // After checking that it is not a duplicate, then it is added into the list of houses
                addressBook.addHouseToHouses(house);
            }
            // After all houses are checked and added, add the seller
            if (!addressBook.hasPerson(seller)) {
                addressBook.addPerson(seller);
            } else {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
        }

        for (JsonAdaptedBuyer jsonAdaptedBuyer : buyers) {
            Buyer buyer = jsonAdaptedBuyer.toModelType();
            if (!addressBook.hasPerson(buyer)) {
                addressBook.addPerson(buyer);
            } else {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
        }
        return addressBook;
    }
}
