package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.house.House;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Seller;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Seller objects.
 */
public class SellerBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmaidasdasl.com";
    public static final String DEFAULT_SELLER_TAG = "Seller";

    private Name name;
    private Phone phone;
    private Email email;
    private ObservableList<House> houses;
    private Set<Tag> tags;

    /**
     * Creates a {@code SellerBuilder} with the default details.
     */
    public SellerBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        houses = FXCollections.observableArrayList();
        tags = new HashSet<>();
        tags.add(new Tag(DEFAULT_SELLER_TAG));
    }

    /**
     * Initializes the SellerBuilder with the data of {@code sellerToCopy}.
     */
    public SellerBuilder(Seller sellerToCopy) {
        name = sellerToCopy.getName();
        phone = sellerToCopy.getPhone();
        email = sellerToCopy.getEmail();
        houses = FXCollections.observableArrayList(sellerToCopy.getHouses());
        tags = new HashSet<>(sellerToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Seller} that we are building.
     */
    public SellerBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Seller} that we are building.
     */
    public SellerBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Seller} that we are building.
     */
    public SellerBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Seller} that we are building.
     */
    public SellerBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code houses} of the {@code Seller} that we are building.
     */
    public SellerBuilder withHouses(House... houses) {
        this.houses = FXCollections.observableArrayList(houses);
        return this;
    }

    public Seller build() {
        return new Seller(name, phone, email, houses);
    }
}
