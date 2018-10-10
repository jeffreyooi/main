package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.group.Meeting;


/**
 * A utility class containing a list of {@code Meeting} objects to be used in tests.
 */
public class TypicalMeetings {

    public static final Meeting DISCUSSION;
    public static final Meeting REHEARSAL;
    public static final Meeting URGENT;
    public static final Meeting WEEKLY;

    static {
        try {
            DISCUSSION = new MeetingBuilder().withTitle("Settle project features")
                    .withTime("09-10-2018@21:01").withLocation("AS1, level 3")
                    .withDescription("Discuss individual components").build();
            REHEARSAL = new MeetingBuilder().withTitle("Rehearse presentation")
                    .withTime("01-01-2018@12:31").withLocation("BIZ2-0304")
                    .withDescription("Practice product demo").build();
            URGENT = new MeetingBuilder().withTitle("Urgent affair")
                    .withTime("18-08-2017@15:21").withLocation("COM2-0202")
                    .withDescription("Urgent meeting on the project direction").build();
            WEEKLY = new MeetingBuilder().withTitle("Weekly meetup")
                    .withTime("22-02-2017@10:10").withLocation("COM1-0202")
                    .withDescription("Weekly report of progress").build();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private TypicalMeetings() {} // prevents instantiation


    public static List<Meeting> getTypicalMeetings() {
        return new ArrayList<>(Arrays.asList(WEEKLY, URGENT, REHEARSAL, DISCUSSION));
    }

}
