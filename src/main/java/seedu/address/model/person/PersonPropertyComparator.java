package seedu.address.model.person;

import java.util.Comparator;

/**
 * Represents the comparators of each property of a person.
 */
public enum PersonPropertyComparator {
    NAME ((p1, p2) -> p1.getName().fullName.compareToIgnoreCase(p2.getName().fullName)),
    PHONE (Comparator.comparing(p -> p.getPhone().value)),
    EMAIL ((p1, p2) -> p1.getEmail().value.compareToIgnoreCase(p2.getEmail().value)),
    ADDRESS ((p1, p2) -> p1.getAddress().value.compareToIgnoreCase(p2.getAddress().value));

    public static final String MESSAGE_PERSON_PROPERTY_CONSTRAINTS =
            "Must be a property of a person. i.e. name, phone, email, address";

    private final Comparator<Person> comparator;

    PersonPropertyComparator(Comparator<Person> comparator) {
        this.comparator = comparator;
    }

    public Comparator<Person> getComparator() {
        return comparator;
    }

    public static PersonPropertyComparator getPersonPropertyComparator(String personProperty)
            throws IllegalArgumentException {
        return PersonPropertyComparator.valueOf(personProperty.toUpperCase());
    }

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
