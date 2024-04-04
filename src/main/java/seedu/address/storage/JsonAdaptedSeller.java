package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.house.House;
import seedu.address.model.person.Person;
import seedu.address.model.person.Seller;

/**
 * Jackson-friendly version of {@link Seller}.
 */
public class JsonAdaptedSeller extends JsonAdaptedPerson {
    private final List<JsonAdaptedHouse> houses = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedSeller}, extends from JsonAdaptedPerson.
     */
    @JsonCreator
    public JsonAdaptedSeller(@JsonProperty("name") String name,
                             @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email,
                             @JsonProperty("houses") List<JsonAdaptedHouse> houses,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        super(name, phone, email, tags);
        if (houses == null) {
            throw new IllegalArgumentException("The 'houses' field is missing!");
        }
        this.houses.addAll(houses);
    }

    /**
     * Converts a given {@code JsonAdaptedSeller} into this class for Jackson use.
     */
    public JsonAdaptedSeller(Seller source) {
        super(source);
        houses.addAll(source.getHouses().stream()
                .map(JsonAdaptedHouse::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted seller object into the model's {@code Seller} object.
     */
    @Override
    public Seller toModelType() throws IllegalValueException {
        Person person = super.toModelType();

        final ObservableList<House> sellerHouses = FXCollections.observableArrayList();
        for (JsonAdaptedHouse house : houses) {
            try {
                sellerHouses.add(house.toModelType());
            } catch (IllegalValueException ive) {
                throw ive;
            } catch (Exception e) {
                throw new IllegalValueException("Error when converting houses: " + e.getMessage());
            }
        }

        return new Seller(person.getName(), person.getPhone(),
                person.getEmail(), sellerHouses);
    }
}
