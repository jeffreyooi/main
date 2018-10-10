package seedu.address.testutil;

import static seedu.address.testutil.TypicalMeetings.WEEKLY;
import static seedu.address.testutil.TypicalPersons.ALICE;

import seedu.address.model.group.Group;

/**
 * A utility class containing a list of {@code Group} objects to be used in tests.
 */
public class TypicalGroup {

    public static final Group PROJECT_2103T = new GroupBuilder().withTitle("CS2103T")
            .withDescription("Project group for module CS2103T").withMeeting(WEEKLY)
            .withNewPerson(ALICE).build();

    public static final Group GROUP_2101 = new GroupBuilder().withTitle("CS2101")
            .withDescription("CS2101 Presentation team").build();


    private TypicalGroup() {} // prevent instantiation

}
