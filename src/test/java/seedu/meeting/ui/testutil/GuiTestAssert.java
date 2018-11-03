package seedu.meeting.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.GroupCardHandle;
import guitests.guihandles.GroupListPanelHandle;
import guitests.guihandles.MeetingCardHandle;
import guitests.guihandles.MeetingListPanelHandle;
import guitests.guihandles.PersonCardHandle;
import guitests.guihandles.PersonListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.meeting.model.group.Group;
import seedu.meeting.model.meeting.Meeting;
import seedu.meeting.model.person.Person;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(PersonCardHandle expectedCard, PersonCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getAddress(), actualCard.getAddress());
        assertEquals(expectedCard.getEmail(), actualCard.getEmail());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the same value as {@code expectedCard} for {@code GroupCard}
     */
    public static void assertGroupCardEquals(GroupCardHandle expectedCard, GroupCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getGroupTitle(), actualCard.getGroupTitle());
    }

    /**
     * Asserts that {@code actualCard} displays the same value as {@code expectedCard} for {@code MeetingCard}
     */
    public static void assertMeetingCardEquals(MeetingCardHandle expectedCard, MeetingCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getMeetingTitle(), actualCard.getMeetingTitle());
        assertEquals(expectedCard.getMeetingDescription(), actualCard.getMeetingDescription());
        assertEquals(expectedCard.getMeetingTime(), actualCard.getMeetingTime());
        assertEquals(expectedCard.getMeetingLocation(), actualCard.getMeetingLocation());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertCardDisplaysPerson(Person expectedPerson, PersonCardHandle actualCard) {
        assertEquals(expectedPerson.getName().fullName, actualCard.getName());
        assertEquals(expectedPerson.getPhone().value, actualCard.getPhone());
        assertEquals(expectedPerson.getEmail().value, actualCard.getEmail());
        assertEquals(expectedPerson.getAddress().value, actualCard.getAddress());
        assertEquals(expectedPerson.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedGroup}
     */
    public static void assertCardDisplaysGroup(Group expectedGroup, GroupCardHandle actualCard) {
        assertEquals(expectedGroup.getTitle().fullTitle, actualCard.getGroupTitle());
        if (expectedGroup.getDescription() != null) {
            assertEquals(expectedGroup.getDescription().statement, actualCard.getGroupDescription());
        }
        if (expectedGroup.getMeeting() != null) {
            assertEquals(expectedGroup.getMeeting().getTitle().fullTitle, actualCard.getGroupMeeting());
        } else {
            assertEquals("", actualCard.getGroupMeeting());
        }
        assertEquals(String.format("%d", expectedGroup.getMembersView().size()), actualCard.getMemberCount());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedMeeting}
     */
    public static void assertCardDisplaysMeeting(Meeting expectedMeeting, MeetingCardHandle actualCard) {
        assertEquals(expectedMeeting.getTitle().fullTitle, actualCard.getMeetingTitle());
        assertEquals(expectedMeeting.getDescription().statement, actualCard.getMeetingDescription());
        assertEquals(expectedMeeting.getTime().toString(), actualCard.getMeetingTime());
        assertEquals(expectedMeeting.getLocation().value, actualCard.getMeetingLocation());
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, Person... persons) {
        for (int i = 0; i < persons.length; i++) {
            personListPanelHandle.navigateToCard(i);
            assertCardDisplaysPerson(persons[i], personListPanelHandle.getPersonCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, List<Person> persons) {
        assertListMatching(personListPanelHandle, persons.toArray(new Person[0]));
    }

    /**
     * Asserts that the list in {@code groupListPanelHandle} displays the details of {@code groups} correctly and in
     * the correct order.
     */
    public static void assertGroupListMatching(GroupListPanelHandle groupListPanelHandle, Group... groups) {
        for (int i = 0; i < groups.length; i++) {
            groupListPanelHandle.navigateToCard(i);
            assertCardDisplaysGroup(groups[i], groupListPanelHandle.getGroupCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code groupListPanelHandle} displays the details of {@code groups} correctly and in
     * the correct order.
     */
    public static void assertGroupListMatching(GroupListPanelHandle groupListPanelHandle, List<Group> groups) {
        assertGroupListMatching(groupListPanelHandle, groups.toArray(new Group[0]));
    }

    /**
     * Asserts that the list in {@code groupListPanelHandle} displays the details of {@code groups} correctly and in
     * the correct order.
     */
    public static void assertMeetingListMatching(MeetingListPanelHandle meetingListPanelHandle, Meeting... meetings) {
        for (int i = 0; i < meetings.length; i++) {
            meetingListPanelHandle.navigateToCard(i);
            assertCardDisplaysMeeting(meetings[i], meetingListPanelHandle.getMeetingCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code groupListPanelHandle} displays the details of {@code groups} correctly and in
     * the correct order.
     */
    public static void assertMeetingListMatching(MeetingListPanelHandle meetingListPanelHandle,
                                                 List<Meeting> meetings) {
        assertMeetingListMatching(meetingListPanelHandle, meetings.toArray(new Meeting[0]));
    }

    /**
     * Asserts the size of the list in {@code personListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(PersonListPanelHandle personListPanelHandle, int size) {
        int numberOfPeople = personListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
