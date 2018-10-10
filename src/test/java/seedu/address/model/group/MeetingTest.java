package seedu.address.model.group;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.testutil.TypicalMeetings.URGENT;
import static seedu.address.testutil.TypicalMeetings.WEEKLY;

import org.junit.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.testutil.MeetingBuilder;


public class MeetingTest {

    @Test
    public void isSameMeeting() throws ParseException {
        // same object -> returns true
        assertTrue(WEEKLY.isSameMeeting(WEEKLY));

        // null -> returns false
        assertFalse(WEEKLY.isSameMeeting(null));

        // different title -> returns false
        Meeting editedWeekly = new MeetingBuilder(WEEKLY).withTitle("Another meetup").build();
        assertFalse(WEEKLY.isSameMeeting(editedWeekly));

        // different date and location -> returns false
        editedWeekly = new MeetingBuilder(WEEKLY).withTime("07-07-2017@12:11").withLocation("off campus").build();
        assertFalse(WEEKLY.isSameMeeting(editedWeekly));

        // same title, same date, same location, different description -> returns true
        editedWeekly = new MeetingBuilder(WEEKLY).withDescription("Weekly report of individual progress").build();
        assertTrue(WEEKLY.isSameMeeting(editedWeekly));
    }

    @Test
    public void equals() throws ParseException {
        // same values -> returns true
        Meeting urgentCopy = new MeetingBuilder(URGENT).build();
        assertTrue(URGENT.isSameMeeting(urgentCopy));

        // same object -> returns true
        assertTrue(URGENT.isSameMeeting(URGENT));

        // null -> returns false
        assertFalse(URGENT.isSameMeeting(null));

        // different type -> returns false
        assertFalse(URGENT.equals(5));

        // different meetings -> returns false
        assertFalse(URGENT.equals(WEEKLY));

        // different title -> returns false
        Meeting editedUrgent = new MeetingBuilder(URGENT).withTitle("Not Urgent").build();
        assertFalse(URGENT.equals(editedUrgent));

        // different date -> returns false
        editedUrgent = new MeetingBuilder(URGENT).withTime("04-04-2014@11:01").build();
        assertFalse(URGENT.equals(editedUrgent));

        // different location -> returns false
        editedUrgent = new MeetingBuilder(URGENT).withLocation("Kent Ridge").build();
        assertFalse(URGENT.equals(editedUrgent));

        // different description -> returns false
        editedUrgent = new MeetingBuilder(URGENT).withDescription("Casual chat over tea").build();
        assertFalse(URGENT.equals(editedUrgent));
    }
}
