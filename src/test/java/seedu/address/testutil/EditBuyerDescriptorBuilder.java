package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditBuyerCommand.EditBuyerDescriptor;
import seedu.address.model.person.Buyer;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditBuyerDescriptor objects.
 */
public class EditBuyerDescriptorBuilder {

    private EditBuyerDescriptor descriptor;

    public EditBuyerDescriptorBuilder() {
        this.descriptor = new EditBuyerDescriptor();
    }

    public EditBuyerDescriptorBuilder(EditBuyerDescriptor descriptor) {
        this.descriptor = new EditBuyerDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditBuyerDescriptor} with fields containing {@code buyer}'s details
     */
    public EditBuyerDescriptorBuilder(Buyer buyer) {
        this.descriptor = new EditBuyerDescriptor();
        this.descriptor.setName(buyer.getName());
        this.descriptor.setPhone(buyer.getPhone());
        this.descriptor.setEmail(buyer.getEmail());
        this.descriptor.setHousingType(buyer.getHousingType());
        this.descriptor.setTags(buyer.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditBuyerDescriptor} that we are building.
     */
    public EditBuyerDescriptorBuilder withName(String name) {
        this.descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditBuyerDescriptor} that we are building.
     */
    public EditBuyerDescriptorBuilder withPhone(String phone) {
        this.descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditBuyerDescriptor} that we are building.
     */
    public EditBuyerDescriptorBuilder withEmail(String email) {
        this.descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the Housing Type of the {@code EditBuyerDescriptor} that we are building.
     */
    public EditBuyerDescriptorBuilder withHousingType(String housingType) {
        this.descriptor.setHousingType(housingType);
        return this;
    }


    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditBuyerDescriptor}
     * that we are building.
     */
    public EditBuyerDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        this.descriptor.setTags(tagSet);
        return this;
    }

    public EditBuyerDescriptor build() {
        return this.descriptor;
    }
}
