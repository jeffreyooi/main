package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.meeting.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.meeting.commons.core.Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX;
import static seedu.meeting.commons.core.Messages.MESSAGE_INVALID_MEETING_DISPLAYED_INDEX;
import static seedu.meeting.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.meeting.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.meeting.logic.commands.SelectCommand.MESSAGE_SELECT_GROUP_SUCCESS;
import static seedu.meeting.logic.commands.SelectCommand.MESSAGE_SELECT_MEETING_SUCCESS;
import static seedu.meeting.logic.commands.SelectCommand.MESSAGE_SELECT_PERSON_SUCCESS;
import static seedu.meeting.testutil.TestUtil.getGroupLastIndex;
import static seedu.meeting.testutil.TestUtil.getGroupMidIndex;
import static seedu.meeting.testutil.TestUtil.getMeetingLastIndex;
import static seedu.meeting.testutil.TestUtil.getMeetingMidIndex;
import static seedu.meeting.testutil.TestUtil.getPersonLastIndex;
import static seedu.meeting.testutil.TestUtil.getPersonMidIndex;
import static seedu.meeting.testutil.TypicalIndexes.INDEX_FIRST_GROUP;
import static seedu.meeting.testutil.TypicalIndexes.INDEX_FIRST_MEETING;
import static seedu.meeting.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.meeting.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.meeting.commons.core.index.Index;
import seedu.meeting.logic.commands.RedoCommand;
import seedu.meeting.logic.commands.SelectCommand;
import seedu.meeting.logic.commands.UndoCommand;
import seedu.meeting.model.Model;

public class SelectCommandSystemTest extends MeetingBookSystemTest {
    @Test
    public void selectPerson() {
        /* -------------------- Perform select operations on the shown unfiltered person list ----------------------- */

        /* Case: select the first card in the person list, command with leading spaces and trailing spaces
         * -> selected
         */
        String command = "   " + SelectCommand.COMMAND_WORD + " p/" + INDEX_FIRST_PERSON.getOneBased() + "   ";
        assertSelectPersonSuccess(command, INDEX_FIRST_PERSON);

        /* Case: select the last card in the person list -> selected */
        Index personCount = getPersonLastIndex(getModel());
        command = SelectCommand.COMMAND_WORD + " p/" + personCount.getOneBased();
        assertSelectPersonSuccess(command, personCount);

        /* Case: undo previous selection -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo selecting last card in the list -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: select the middle card in the person list -> selected */
        Index middleIndex = getPersonMidIndex(getModel());
        command = SelectCommand.COMMAND_WORD + " p/" + middleIndex.getOneBased();
        assertSelectPersonSuccess(command, middleIndex);

        /* Case: select the current selected person card -> selected */
        assertSelectPersonSuccess(command, middleIndex);

        /* --------------------- Perform select operations on the shown filtered person list ------------------------ */

        /* Case: filtered person list, select index within bounds of MeetingBook but out of bounds of person list
         * -> rejected
         */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getMeetingBook().getPersonList().size();
        assertCommandFailure(SelectCommand.COMMAND_WORD + " p/" + invalidIndex, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: filtered person list, select index within bounds of MeetingBook and person list -> selected */
        Index validIndex = Index.fromOneBased(1);
        assertTrue(validIndex.getZeroBased() < getModel().getFilteredPersonList().size());
        command = SelectCommand.COMMAND_WORD + " p/" + validIndex.getOneBased();
        assertSelectPersonSuccess(command, validIndex);

        /* -------------------------------- Perform invalid select person operations -------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(SelectCommand.COMMAND_WORD + " p/" + 0,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(SelectCommand.COMMAND_WORD + " p/" + -1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredPersonList().size() + 1;
        assertCommandFailure(SelectCommand.COMMAND_WORD + " p/" + invalidIndex, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(SelectCommand.COMMAND_WORD + " p/abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(SelectCommand.COMMAND_WORD + " p/1 abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("SeLeCt p/1", MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void selectGroup() {

        /* -------------------- Perform select operations on the shown unfiltered group list ------------------------ */

        /* Case: select the first card in the group list, command with leading spaces and trailing spaces
         * -> selected
         */
        String command = "   " + SelectCommand.COMMAND_WORD + " g/" + INDEX_FIRST_GROUP.getOneBased() + "    ";
        assertSelectGroupSuccess(command, INDEX_FIRST_GROUP);

        /* Case: select the last card in the group list -> selected */
        Index groupCount = getGroupLastIndex(getModel());
        command = SelectCommand.COMMAND_WORD + " g/" + groupCount.getOneBased();
        assertSelectGroupSuccess(command, groupCount);

        /* Case: undo previous selection -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo selecting last group card in the list -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: select the middle card in the group list -> selected */
        Index middleIndex = getGroupMidIndex(getModel());
        command = SelectCommand.COMMAND_WORD + " g/" + middleIndex.getOneBased();
        assertSelectGroupSuccess(command, middleIndex);

        /* Case: select the current selected group card -> selected */
        assertSelectGroupSuccess(command, middleIndex);

        /* -------------------------------- Perform invalid select person operations -------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(SelectCommand.COMMAND_WORD + " g/" + 0,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(SelectCommand.COMMAND_WORD + " g/" + -1,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        int invalidIndex = getModel().getFilteredGroupList().size() + 1;
        assertCommandFailure(SelectCommand.COMMAND_WORD + " g/" + invalidIndex, MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(SelectCommand.COMMAND_WORD + " g/abc",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(SelectCommand.COMMAND_WORD + " g/1 abc",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("SeLeCt g/1", MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void selectMeeting() {
        /* ------------------- Perform select operations on the shown unfiltered meeting list ---------------------- */

        /* Case: select the first card in the meeting list, command with leading spaces and trailing spaces
         * -> selected
         */
        String command = "   " + SelectCommand.COMMAND_WORD + " m/" + INDEX_FIRST_MEETING.getOneBased() + "   ";
        assertSelectMeetingSuccess(command, INDEX_FIRST_MEETING);

        /* Case: select the last card in the meeting list -> selected */
        Index meetingCount = getMeetingLastIndex(getModel());
        command = SelectCommand.COMMAND_WORD + " m/" + meetingCount.getOneBased();
        assertSelectMeetingSuccess(command, meetingCount);

        /* Case: undo previous selection -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo selecting last card in the list -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: select the middle card in the meeting list -> selected */
        Index middleIndex = getMeetingMidIndex(getModel());
        command = SelectCommand.COMMAND_WORD + " m/" + middleIndex.getOneBased();
        assertSelectMeetingSuccess(command, middleIndex);

        /* Case: select the current selected meeting card -> selected */
        assertSelectMeetingSuccess(command, middleIndex);

        /* -------------------------------- Perform invalid select person operations -------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(SelectCommand.COMMAND_WORD + " m/" + 0,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(SelectCommand.COMMAND_WORD + " m/" + -1,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        int invalidIndex = getModel().getFilteredMeetingList().size() + 1;
        assertCommandFailure(SelectCommand.COMMAND_WORD + " m/" + invalidIndex,
            MESSAGE_INVALID_MEETING_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(SelectCommand.COMMAND_WORD + " m/abc",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(SelectCommand.COMMAND_WORD + " m/1 abc",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("SeLeCt m/1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing select command with the
     * {@code expectedSelectedCardIndex} of the selected person.<br>
     * 4. {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Selected card is at {@code expectedSelectedCardIndex} and the browser url is updated accordingly.<br>
     * 6. Status bar remains unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code MeetingBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see MeetingBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see MeetingBookSystemTest#assertSelectedPersonCardChanged(Index)
     */
    private void assertSelectPersonSuccess(String command, Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        String expectedResultMessage = String.format(MESSAGE_SELECT_PERSON_SUCCESS,
            expectedSelectedCardIndex.getOneBased());
        int preExecutionSelectedCardIndex = getPersonListPanel().getSelectedCardIndex();

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        assertPersonListDisplaysExpected(expectedModel);
        if (preExecutionSelectedCardIndex == expectedSelectedCardIndex.getZeroBased()) {
            assertSelectedPersonCardUnchanged();

        } else {
            assertSelectedPersonCardChanged(expectedSelectedCardIndex);
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing select command with the
     * {@code expectedSelectedCardIndex} of the selected person.<br>
     * 4. {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Selected card is at {@code expectedSelectedCardIndex} and the browser url is updated accordingly.<br>
     * 6. Status bar remains unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code MeetingBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see MeetingBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see MeetingBookSystemTest#assertSelectedPersonCardChanged(Index)
     */
    private void assertSelectGroupSuccess(String command, Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        String expectedResultMessage = String.format(MESSAGE_SELECT_GROUP_SUCCESS,
            expectedSelectedCardIndex.getOneBased());
        int preExecutionSelectedCardIndex = getPersonListPanel().getSelectedCardIndex();

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        assertGroupListDisplaysExpected(expectedModel);
        if (preExecutionSelectedCardIndex == expectedSelectedCardIndex.getZeroBased()) {
            assertSelectedGroupCardUnchanged();
        } else {
            assertSelectedGroupCardChanged(expectedSelectedCardIndex);
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing select command with the
     * {@code expectedSelectedCardIndex} of the selected meeting.<br>
     * 4. {@code Storage} and {@code MeetingListPanel} remain unchanged.<br>
     * 5. Selected card is at {@code expectedSelectedCardIndex}.<br>
     * 6. Status bar remains unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code MeetingBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see MeetingBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see MeetingBookSystemTest#assertSelectedPersonCardChanged(Index)
     */
    private void assertSelectMeetingSuccess(String command, Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        String expectedResultMessage = String.format(MESSAGE_SELECT_MEETING_SUCCESS,
            expectedSelectedCardIndex.getOneBased());
        int preExecutionSelectedCardIndex = getPersonListPanel().getSelectedCardIndex();

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        assertMeetingListDisplaysExpected(expectedModel);
        if (preExecutionSelectedCardIndex == expectedSelectedCardIndex.getZeroBased()) {
            assertSelectedMeetingCardUnchanged();
        } else {
            assertSelectedMeetingCardChanged(expectedSelectedCardIndex);
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code MeetingBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see MeetingBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedPersonCardUnchanged();
        assertSelectedGroupCardUnchanged();
        assertSelectedMeetingCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
