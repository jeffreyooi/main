package seedu.address.model.group;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.person.Address;

/**
 * Represents a Meeting for people in the same group in the address book.
 * Guarantees: Title, date, location and description are present and not null.
 * Field values are validated, immutable.
 */
public class Meeting {

    // Identity fields
    private final Title title;

    // Data fields
    private final TimeStamp time;
    private final Address location;
    private final Description description;

    /**
     * Constructor of a Meeting object, which contains information
     * pertaining to the organisation of the next group meeting.
     *
     * Title, date, location and description must be present and non-null.
     *
     * @param title The title of the meeting
     * @param time The exact time of group meeting
     * @param location The exact place of meeting
     * @param description The informative description about the content of meeting
     */
    public Meeting(Title title, TimeStamp time, Address location,
                   Description description) {
        requireAllNonNull(title, time, location, description);
        this.title = title;
        this.time = time;
        this.location = location;
        this.description = description;
    }

    public Title getTitle() {
        return title;
    }

    public TimeStamp getTime() {
        return time;
    }

    public Address getLocation() {
        return location;
    }

    public Description getDescription() {
        return description;
    }

    /**
     * Returns true if both meetings have the same title, date and location.
     * This defines a weaker notion of equality between two meetings.
     */
    public boolean isSameMeeting(Meeting otherMeeting) {
        if (otherMeeting == this) {
            return true;
        }

        return otherMeeting != null
                && otherMeeting.getTitle().equals(getTitle())
                && otherMeeting.getTime().equals(getTime())
                && otherMeeting.getLocation().equals(getLocation());
    }

    /**
     * Returns true if both meetings have the same identity and data fields.
     * This defines a stronger notion of equality between two meetings.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Meeting)) {
            return false;
        }

        Meeting otherMeeting = (Meeting) other;
        return otherMeeting.getTitle().equals(getTitle())
                && otherMeeting.getTime().equals(getTime())
                && otherMeeting.getLocation().equals(getLocation())
                && otherMeeting.getDescription().equals(getDescription());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, time, location, description);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle() + "\n")
                .append("Date: ")
                .append(getTime() + "\n")
                .append("Location: ")
                .append(getLocation() + "\n")
                .append("Description: ")
                .append(getDescription() + "\n");

        return builder.toString();
    }
}
