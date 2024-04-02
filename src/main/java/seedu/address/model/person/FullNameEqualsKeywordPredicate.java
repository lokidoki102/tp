package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s full name matches the keyword given.
 */
public class FullNameEqualsKeywordPredicate implements Predicate<Person> {
    private final String keyword;

    public FullNameEqualsKeywordPredicate(String keyword) {
        this.keyword = keyword.toLowerCase();
    }

    @Override
    public boolean test(Person person) {
        return person.getName().fullName.equalsIgnoreCase(keyword);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FullNameEqualsKeywordPredicate)) {
            return false;
        }

        FullNameEqualsKeywordPredicate otherPredicate = (FullNameEqualsKeywordPredicate) other;
        return keyword.equalsIgnoreCase(otherPredicate.keyword);
    }
}
