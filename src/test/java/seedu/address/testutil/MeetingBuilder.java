package seedu.address.testutil;

import static seedu.address.logic.parser.ParserUtil.parseTimeStamp;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.Description;
import seedu.address.model.group.Meeting;
import seedu.address.model.group.TimeStamp;
import seedu.address.model.group.Title;
import seedu.address.model.person.Address;

/**
 * A utility class to help with building Meeting object.
 */
public class MeetingBuilder {

    public static final String DEFAULT_TITLE = "Weekly meetup";
    public static final String DEFAULT_TIMESTAMP = "22-01-2017@10:10";
    public static final String DEFAULT_LOCATION = "COM1-0202";
    public static final String DEFAULT_DESCRIPTION = "To discuss the individual progress";

    private Title title;
    private TimeStamp time;
    private Address location;
    private Description description;

    public MeetingBuilder() throws ParseException {
        title = new Title(DEFAULT_TITLE);
        time = parseTimeStamp(DEFAULT_TIMESTAMP);
        location = new Address(DEFAULT_LOCATION);
        description = new Description(DEFAULT_DESCRIPTION);
    }

    /**
     * Initializes the MeetingBuilder with the data of {@code meetingToCopy}.
     */
    public MeetingBuilder(Meeting meetingToCopy) {
        title = meetingToCopy.getTitle();
        time = meetingToCopy.getTime();
        location = meetingToCopy.getLocation();
        description = meetingToCopy.getDescription();
    }

    /**
     * Sets the {@code Title} of the {@code Meeting} that we are building.
     */
    public MeetingBuilder withTitle(String title) {
        this.title = new Title(title);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Meeting} that we are building.
     */
    public MeetingBuilder withTime(String time) throws ParseException {
        this.time = parseTimeStamp(time);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Meeting} that we are building.
     */
    public MeetingBuilder withLocation(String location) {
        this.location = new Address(location);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Meeting} that we are building.
     */
    public MeetingBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Build a new meeting from MeetingBuilder
     *
     * @return The new meeting
     */
    public Meeting build() {
        Meeting meeting = new Meeting(title, time, location, description);
        return meeting;
    }
}
