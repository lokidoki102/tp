package seedu.address.storage;

import java.util.HashSet;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Budget;
import seedu.address.model.person.Buyer;
import seedu.address.model.person.Person;

/**
 * Jackson-friendly version of {@link Buyer}.
 */
public class JsonAdaptedBuyer extends JsonAdaptedPerson {

    private final String budget;

    /**
     * Constructs a {@code JsonAdaptedBuyer}, extends from JsonAdaptedPerson
     */
    @JsonCreator
    public JsonAdaptedBuyer(@JsonProperty("name") String name,
                             @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email,
                             @JsonProperty("housingType") String housingType,
                             @JsonProperty("budget") String budget,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        super(name, phone, email, housingType, tags);
        this.budget = budget;
    }

    /**
     * Converts a given {@code JsonAdaptedBuyer} into this class for Jackson use.
     */
    public JsonAdaptedBuyer(Buyer source) {
        super(source);
        budget = source.getBudget().value;
    }

    /**
     * Converts this Jackson-friendly adapted buyer object into the model's {@code Buyer} object.
     */
    @Override
    public Buyer toModelType() throws IllegalValueException {

        if (budget == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Budget.class.getSimpleName()));
        }
        if (!Budget.isValidBudget(budget)) {
            throw new IllegalValueException(Budget.MESSAGE_CONSTRAINTS);
        }
        final Budget modelBudget = new Budget(budget);

        Person person = super.toModelType();
        return new Buyer(person.getName(), person.getPhone(), person.getEmail(),
                person.getHousingType(), modelBudget, new HashSet<>(person.getTags()));
    }
}
