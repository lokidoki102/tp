package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.house.Block;
import seedu.address.model.house.Condominium;
import seedu.address.model.house.Hdb;
import seedu.address.model.house.House;
import seedu.address.model.house.Landed;
import seedu.address.model.house.Level;
import seedu.address.model.house.PostalCode;
import seedu.address.model.house.Street;
import seedu.address.model.house.UnitNumber;

/**
 * Jackson-friendly version of {@link House}.
 */
public class JsonAdaptedHouse {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "House's %s field is missing!";
    private final String type;
    private final String block;
    private final String level;
    private final String postalCode;
    private final String street;
    private final String unitNumber;

    /**
     * Constructs a {@code JsonAdaptedHouse} with the given house details.
     */
    @JsonCreator
    public JsonAdaptedHouse(@JsonProperty("type") String type,
                            @JsonProperty("block") String block,
                            @JsonProperty("level") String level,
                            @JsonProperty("postalCode") String postalCode,
                            @JsonProperty("street") String street,
                            @JsonProperty("unitNumber") String unitNumber) {
        this.type = type;
        this.block = block;
        this.level = level;
        this.postalCode = postalCode;
        this.street = street;
        this.unitNumber = unitNumber;
    }

    /**
     * Converts a given {@code House} into this class for Jackson use.
     */
    public JsonAdaptedHouse(House source) {
        if (source instanceof Condominium) {
            Condominium condominium = (Condominium) source;
            this.block = condominium.getBlock() == null ? null : condominium.getBlock().value;
            this.level = condominium.getLevel() == null ? null : condominium.getLevel().value;
            this.type = "Condominium";
        } else if (source instanceof Hdb) {
            Hdb hdb = (Hdb) source;
            this.block = hdb.getBlock() == null ? null : hdb.getBlock().value;
            this.level = hdb.getLevel() == null ? null : hdb.getLevel().value;
            this.type = "Hdb";
        } else {
            this.block = null;
            this.level = null;
            this.type = "Landed";
        }
        this.postalCode = source.getPostalCode().value;
        this.street = source.getStreet().value;
        this.unitNumber = source.getUnitNumber().value;
    }

    /**
     * Converts this Jackson-friendly adapted house object into the model's {@code House} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted house.
     */
    public House toModelType() throws IllegalValueException {
        if (postalCode == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    PostalCode.class.getSimpleName()));
        }
        if (!PostalCode.isValidPostalCode(postalCode)) {
            throw new IllegalValueException(PostalCode.MESSAGE_CONSTRAINTS);
        }
        final PostalCode modelPostalCode = new PostalCode(postalCode);

        if (street == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Street.class.getSimpleName()));
        }
        if (!Street.isValidStreet(street)) {
            throw new IllegalValueException(Street.MESSAGE_CONSTRAINTS);
        }
        final Street modelStreet = new Street(street);

        if (unitNumber == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    UnitNumber.class.getSimpleName()));
        }
        if (!UnitNumber.isValidUnitNumber(unitNumber)) {
            throw new IllegalValueException(UnitNumber.MESSAGE_CONSTRAINTS);
        }
        final UnitNumber modelUnitNumber = new UnitNumber(unitNumber);

        if ("Condominium".equals(type)) {
            Block modelBlock = block != null ? new Block(block) : null;
            Level modelLevel = level != null ? new Level(level) : null;
            if (modelBlock != null) {
                return new Condominium(modelLevel, modelPostalCode, modelStreet, modelUnitNumber, modelBlock);
            } else {
                return new Condominium(modelLevel, modelPostalCode, modelStreet, modelUnitNumber);
            }
        } else if ("Hdb".equals(type)) {
            Block modelBlock = block != null ? new Block(block) : null;
            Level modelLevel = level != null ? new Level(level) : null;
            return new Hdb(modelLevel, modelPostalCode, modelStreet, modelUnitNumber, modelBlock);
        } else if ("Landed".equals(type)) {
            return new Landed(modelUnitNumber, modelPostalCode, modelStreet);
        } else {
            throw new IllegalValueException("Unknown House Type");
        }
    }
}
