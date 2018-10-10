package seedu.address.model.group;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalGroup.GROUP_2101;
import static seedu.address.testutil.TypicalGroup.PROJECT_2103T;
import static seedu.address.testutil.TypicalMeetings.URGENT;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.Test;

import seedu.address.testutil.GroupBuilder;


public class GroupTest {

    @Test
    public void isSameGroup() {
        // same object -> returns true
        assertTrue(PROJECT_2103T.isSameGroup(PROJECT_2103T));

        // null -> returns false
        assertFalse(PROJECT_2103T.isSameGroup(null));

        // different title -> returns false
        Group editedProject = new GroupBuilder(PROJECT_2103T).withTitle("CS2113").build();
        assertFalse(editedProject.isSameGroup(PROJECT_2103T));

        // different description -> returns false
        editedProject = new GroupBuilder(PROJECT_2103T).withDescription("Discussion group for CS2103T").build();
        assertFalse(editedProject.isSameGroup(PROJECT_2103T));

        // same title, same description, different meeting -> returns true
        editedProject = new GroupBuilder(PROJECT_2103T).withMeeting(URGENT).build();
        assertTrue(editedProject.isSameGroup(PROJECT_2103T));

        // same title, same description, different members -> returns true
        editedProject = new GroupBuilder(PROJECT_2103T).withNewPerson(BOB).build();
        assertTrue(editedProject.isSameGroup(PROJECT_2103T));

        // same members -> returns true
        editedProject = new GroupBuilder(PROJECT_2103T).withNewPerson(BOB).withRemovedPerson(BOB).build();
        assertTrue(editedProject.isSameGroup(PROJECT_2103T));
    }


    @Test
    public void equals() {
        // same values -> returns true
        Group projectCopy = new GroupBuilder(GROUP_2101).build();
        assertTrue(GROUP_2101.equals(projectCopy));

        // same object -> returns true
        assertTrue(GROUP_2101.equals(GROUP_2101));

        // null -> returns false
        assertFalse(GROUP_2101.equals(null));

        // different type -> returns false
        assertFalse(GROUP_2101.equals(2));

        // different groups -> returns false
        assertFalse(GROUP_2101.equals(PROJECT_2103T));

        // different title -> returns false
        Group editedGroup = new GroupBuilder(GROUP_2101).withTitle("CS2102").build();
        assertFalse(editedGroup.equals(GROUP_2101));

        // different description -> returns false
        editedGroup = new GroupBuilder(GROUP_2101).withDescription("Discussion group for CS2103T").build();
        assertFalse(editedGroup.equals(GROUP_2101));

        // same title, same description, different meeting -> returns false
        editedGroup = new GroupBuilder(GROUP_2101).withMeeting(URGENT).build();
        assertFalse(editedGroup.equals(GROUP_2101));

        // same title, same description, different members -> returns false
        editedGroup = new GroupBuilder(GROUP_2101).withNewPerson(BOB).build();
        assertFalse(editedGroup.equals(GROUP_2101));

        // same members -> returns true
        editedGroup = new GroupBuilder(GROUP_2101).withNewPerson(BOB).withRemovedPerson(BOB).build();
        assertTrue(editedGroup.equals(GROUP_2101));
    }
}
