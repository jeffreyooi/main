package seedu.meeting.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.meeting.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.meeting.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.meeting.logic.commands.CommandTestUtil.showMeetingAtIndex;
import static seedu.meeting.testutil.TypicalIndexes.INDEX_FIRST_MEETING;
import static seedu.meeting.testutil.TypicalIndexes.INDEX_SECOND_MEETING;
import static seedu.meeting.testutil.TypicalIndexes.INDEX_THIRD_MEETING;
import static seedu.meeting.testutil.TypicalMeetingBook.getTypicalMeetingBook;

import org.junit.Rule;
import org.junit.Test;

import seedu.meeting.commons.core.Messages;
import seedu.meeting.commons.core.index.Index;
import seedu.meeting.commons.events.BaseEvent;
import seedu.meeting.commons.events.ui.JumpToMeetingListRequestEvent;
import seedu.meeting.logic.CommandHistory;
import seedu.meeting.model.Model;
import seedu.meeting.model.ModelManager;
import seedu.meeting.model.UserPrefs;
import seedu.meeting.ui.testutil.EventsCollectorRule;

public class SelectMeetingCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalMeetingBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalMeetingBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredMeetingList_success() {
        Index lastMeetingIndex = Index.fromOneBased(model.getFilteredMeetingList().size());

        assertExecutionSuccess(INDEX_FIRST_MEETING);
        assertExecutionSuccess(INDEX_THIRD_MEETING);
        assertExecutionSuccess(lastMeetingIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredMeetingList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredMeetingList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_MEETING_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredMeetingList_success() {
        showMeetingAtIndex(model, INDEX_FIRST_MEETING);
        showMeetingAtIndex(expectedModel, INDEX_FIRST_MEETING);

        assertExecutionSuccess(INDEX_FIRST_MEETING);
    }

    @Test
    public void execute_invalidIndexFilteredMeetingList_failure() {
        showMeetingAtIndex(model, INDEX_FIRST_MEETING);
        showMeetingAtIndex(expectedModel, INDEX_FIRST_MEETING);

        Index outOfBoundsIndex = INDEX_SECOND_MEETING;
        // ensures that outOfBoundIndex is still in bounds of MeetingBook list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getMeetingBook().getMeetingList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_MEETING_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectCommand selectFirstCommand = new SelectMeetingCommand(INDEX_FIRST_MEETING);
        SelectCommand selectSecondCommand = new SelectMeetingCommand(INDEX_SECOND_MEETING);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectCommand selectFirstCommandCopy =
            new SelectPersonCommand(INDEX_FIRST_MEETING);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectMeetingCommand} with the given {@code index}, and checks that
     * {@code JumpToMeetingListRequestEvent} is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        SelectCommand selectCommand = new SelectMeetingCommand(index);
        String expectedMessage = String.format(SelectMeetingCommand.MESSAGE_SELECT_MEETING_SUCCESS,
            index.getOneBased());

        assertCommandSuccess(selectCommand, model, commandHistory, expectedMessage, expectedModel);

        BaseEvent baseEvent = eventsCollectorRule.eventsCollector.getMostRecent();
        JumpToMeetingListRequestEvent lastEvent = (JumpToMeetingListRequestEvent) baseEvent;
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectCommand selectCommand = new SelectMeetingCommand(index);
        assertCommandFailure(selectCommand, model, commandHistory, expectedMessage);
        assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
    }
}
