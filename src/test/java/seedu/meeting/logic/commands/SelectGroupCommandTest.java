package seedu.meeting.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.meeting.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.meeting.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.meeting.logic.commands.CommandTestUtil.showGroupAtIndex;
import static seedu.meeting.testutil.TypicalIndexes.INDEX_FIRST_GROUP;
import static seedu.meeting.testutil.TypicalIndexes.INDEX_SECOND_GROUP;
import static seedu.meeting.testutil.TypicalIndexes.INDEX_THIRD_GROUP;
import static seedu.meeting.testutil.TypicalMeetingBook.getTypicalMeetingBook;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;

import seedu.meeting.commons.core.Messages;
import seedu.meeting.commons.core.index.Index;
import seedu.meeting.commons.events.BaseEvent;
import seedu.meeting.commons.events.ui.JumpToGroupListRequestEvent;
import seedu.meeting.logic.CommandHistory;
import seedu.meeting.model.Model;
import seedu.meeting.model.ModelManager;
import seedu.meeting.model.UserPrefs;
import seedu.meeting.model.group.Group;
import seedu.meeting.model.group.util.GroupContainsPersonPredicate;
import seedu.meeting.ui.testutil.EventsCollectorRule;

public class SelectGroupCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalMeetingBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalMeetingBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredGroupList_success() {
        Index lastGroupIndex = Index.fromOneBased(model.getFilteredGroupList().size());

        Group group = expectedModel.getFilteredGroupList().get(INDEX_FIRST_GROUP.getZeroBased());
        expectedModel.updateFilteredPersonList(new GroupContainsPersonPredicate(Arrays.asList(group)));
        assertExecutionSuccess(INDEX_FIRST_GROUP);

        group = expectedModel.getFilteredGroupList().get(INDEX_THIRD_GROUP.getZeroBased());
        expectedModel.updateFilteredPersonList(new GroupContainsPersonPredicate(Arrays.asList(group)));
        assertExecutionSuccess(INDEX_THIRD_GROUP);

        group = expectedModel.getFilteredGroupList().get(lastGroupIndex.getZeroBased());
        expectedModel.updateFilteredPersonList(new GroupContainsPersonPredicate(Arrays.asList(group)));
        assertExecutionSuccess(lastGroupIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredGroupList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredGroupList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredGroupList_success() {
        showGroupAtIndex(model, INDEX_FIRST_GROUP);
        showGroupAtIndex(expectedModel, INDEX_FIRST_GROUP);

        assertExecutionSuccess(INDEX_FIRST_GROUP);
    }

    @Test
    public void execute_invalidIndexFilteredGroupList_failure() {
        showGroupAtIndex(model, INDEX_FIRST_GROUP);
        showGroupAtIndex(expectedModel, INDEX_FIRST_GROUP);

        Index outOfBoundsIndex = INDEX_SECOND_GROUP;
        // ensures that outOfBoundIndex is still in bounds of MeetingBook list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getMeetingBook().getGroupList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectCommand selectFirstCommand = new SelectGroupCommand(INDEX_FIRST_GROUP);
        SelectCommand selectSecondCommand = new SelectGroupCommand(INDEX_SECOND_GROUP);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectCommand selectFirstCommandCopy =
            new SelectGroupCommand(INDEX_FIRST_GROUP);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that
     * {@code JumpToGroupListRequestEvent} is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        SelectCommand selectCommand = new SelectGroupCommand(index);
        String expectedMessage = String.format(SelectGroupCommand.MESSAGE_SELECT_GROUP_SUCCESS, index.getOneBased());

        assertCommandSuccess(selectCommand, model, commandHistory, expectedMessage, expectedModel);

        BaseEvent baseEvent = eventsCollectorRule.eventsCollector.getMostRecent();
        JumpToGroupListRequestEvent lastEvent = (JumpToGroupListRequestEvent) baseEvent;
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectCommand selectCommand = new SelectGroupCommand(index);
        assertCommandFailure(selectCommand, model, commandHistory, expectedMessage);
        assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
    }
}
