package systemtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.meeting.ui.StatusBarFooter.SYNC_STATUS_INITIAL;
import static seedu.meeting.ui.StatusBarFooter.SYNC_STATUS_UPDATED;
import static seedu.meeting.ui.testutil.GuiTestAssert.assertGroupListMatching;
import static seedu.meeting.ui.testutil.GuiTestAssert.assertListMatching;
import static seedu.meeting.ui.testutil.GuiTestAssert.assertMeetingListMatching;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;

import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.GroupListPanelHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.MainWindowHandle;
import guitests.guihandles.MeetingListPanelHandle;
import guitests.guihandles.PersonListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.StatusBarFooterHandle;
import seedu.meeting.TestApp;
import seedu.meeting.commons.core.EventsCenter;
import seedu.meeting.commons.core.index.Index;
import seedu.meeting.logic.commands.ClearCommand;
import seedu.meeting.logic.commands.FindGroupCommand;
import seedu.meeting.logic.commands.FindMeetingCommand;
import seedu.meeting.logic.commands.FindPersonCommand;
import seedu.meeting.logic.commands.ListCommand;
import seedu.meeting.logic.commands.SelectCommand;
import seedu.meeting.model.MeetingBook;
import seedu.meeting.model.Model;
import seedu.meeting.testutil.TypicalMeetingBook;
import seedu.meeting.ui.CommandBox;

/**
 * A system test class for MeetingBook, which provides access to handles of GUI components and helper methods
 * for test verification.
 */
public abstract class MeetingBookSystemTest {
    @ClassRule
    public static ClockRule clockRule = new ClockRule();

    private static final List<String> COMMAND_BOX_DEFAULT_STYLE = Arrays.asList("text-input", "text-field");
    private static final List<String> COMMAND_BOX_ERROR_STYLE =
            Arrays.asList("text-input", "text-field", CommandBox.ERROR_STYLE_CLASS);

    private MainWindowHandle mainWindowHandle;
    private TestApp testApp;
    private SystemTestSetupHelper setupHelper;

    @BeforeClass
    public static void setupBeforeClass() {
        SystemTestSetupHelper.initialize();
    }

    @Before
    public void setUp() {
        setupHelper = new SystemTestSetupHelper();
        testApp = setupHelper.setupApplication(this::getInitialData, getDataFileLocation());
        mainWindowHandle = setupHelper.setupMainWindowHandle();

        assertApplicationStartingStateIsCorrect();
    }

    @After
    public void tearDown() {
        setupHelper.tearDownStage();
        EventsCenter.clearSubscribers();
    }

    /**
     * Returns the data to be loaded into the file in {@link #getDataFileLocation()}.
     */
    protected MeetingBook getInitialData() {
        return TypicalMeetingBook.getTypicalMeetingBook();
    }

    /**
     * Returns the directory of the data file.
     */
    protected Path getDataFileLocation() {
        return TestApp.SAVE_LOCATION_FOR_TESTING;
    }

    public MainWindowHandle getMainWindowHandle() {
        return mainWindowHandle;
    }

    public CommandBoxHandle getCommandBox() {
        return mainWindowHandle.getCommandBox();
    }

    public PersonListPanelHandle getPersonListPanel() {
        return mainWindowHandle.getPersonListPanel();
    }

    public MainMenuHandle getMainMenu() {
        return mainWindowHandle.getMainMenu();
    }

    public GroupListPanelHandle getGroupListPanel() {
        return mainWindowHandle.getGroupListPanel();
    }

    public MeetingListPanelHandle getMeetingListPanel() {
        return mainWindowHandle.getMeetingListPanel();
    }

    public StatusBarFooterHandle getStatusBarFooter() {
        return mainWindowHandle.getStatusBarFooter();
    }

    public ResultDisplayHandle getResultDisplay() {
        return mainWindowHandle.getResultDisplay();
    }

    /**
     * Executes {@code command} in the application's {@code CommandBox}.
     * Method returns after UI components have been updated.
     */
    protected void executeCommand(String command) {
        rememberStates();
        // Injects a fixed clock before executing a command so that the time stamp shown in the status bar
        // after each command is predictable and also different from the previous command.
        clockRule.setInjectedClockToCurrentTime();

        mainWindowHandle.getCommandBox().run(command);
    }

    /**
     * Displays all persons in the MeetingBook.
     */
    protected void showAllPersons() {
        executeCommand(ListCommand.COMMAND_WORD + " " + ListCommand.COMMAND_PARAM_PERSON);
        assertEquals(getModel().getMeetingBook().getPersonList().size(), getModel().getFilteredPersonList().size());
    }

    /**
     * Displays all persons with any parts of their names matching {@code keyword} (case-insensitive).
     */
    protected void showPersonsWithName(String keyword) {
        executeCommand(FindPersonCommand.COMMAND_WORD + " " + FindPersonCommand.FIND_PERSON_PARAM + " " + keyword);
        assertTrue(getModel().getFilteredPersonList().size() < getModel().getMeetingBook().getPersonList().size());
    }

    /**
     * Selects the person at {@code index} of the displayed list.
     */
    protected void selectPerson(Index index) {
        executeCommand(SelectCommand.COMMAND_WORD + " p/" + index.getOneBased());
        assertEquals(index.getZeroBased(), getPersonListPanel().getSelectedCardIndex());
    }

    /**
     * Deletes all persons in the MeetingBook.
     */
    protected void deleteAllPersons() {
        executeCommand(ClearCommand.COMMAND_WORD);
        assertEquals(0, getModel().getMeetingBook().getPersonList().size());
    }

    /**
     * Displays all groups with any parts of their title matching {@code keyword} (case-insensitive).
     */
    protected void showGroupsWithName(String keyword) {
        executeCommand(FindGroupCommand.COMMAND_WORD + " " + FindGroupCommand.FIND_GROUP_PARAM + " " + keyword);
        assertTrue(getModel().getFilteredGroupList().size() < getModel().getMeetingBook().getGroupList().size());
    }

    /**
     * Displays all meetings with any parts of their title matching {@code keyword} (case-insensitive).
     */
    protected void showMeetingsWithName(String keyword) {
        executeCommand(FindMeetingCommand.COMMAND_WORD + " " + FindMeetingCommand.FIND_MEETING_PARAM + " " + keyword);
        assertTrue(getModel().getFilteredMeetingList().size() < getModel().getMeetingBook().getMeetingList().size());
    }

    /**
     * Asserts that the {@code CommandBox} displays {@code expectedCommandInput}, the {@code ResultDisplay} displays
     * {@code expectedResultMessage}, the storage contains the same person objects as {@code expectedModel}
     * and the person list panel displays the persons in the model correctly.
     */
    protected void assertApplicationDisplaysExpected(String expectedCommandInput, String expectedResultMessage,
            Model expectedModel) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
        assertEquals(new MeetingBook(expectedModel.getMeetingBook()), testApp.readStorageMeetingBook());
    }

    /**
     * Asserts that the person list panel displays the persons in the model correctly.
     */
    protected void assertPersonListDisplaysExpected(Model expectedModel) {
        assertListMatching(getPersonListPanel(), expectedModel.getFilteredPersonList());
    }

    /**
     * Asserts that the group list panel displays the groups in the model correctly.
     */
    protected void assertGroupListDisplaysExpected(Model expectedModel) {
        assertGroupListMatching(getGroupListPanel(), expectedModel.getFilteredGroupList());
    }

    /**
     * Asserts that the meeting list panel displays the meetings in the model correctly.
     */
    protected void assertMeetingListDisplaysExpected(Model expectedModel) {
        assertMeetingListMatching(getMeetingListPanel(), expectedModel.getFilteredMeetingList());
    }

    /**
     * Calls {@code PersonListPanelHandle} and {@code StatusBarFooterHandle} to remember their current state.
     */
    private void rememberStates() {
        StatusBarFooterHandle statusBarFooterHandle = getStatusBarFooter();
        getGroupListPanel().rememberSelectedGroupCard();
        getMeetingListPanel().rememberSelectedMeetingCard();
        statusBarFooterHandle.rememberSaveLocation();
        statusBarFooterHandle.rememberSyncStatus();
        getPersonListPanel().rememberSelectedPersonCard();
    }

    /**
     * Asserts that the previously selected card is now deselected and the browser's url remains displaying the details
     * of the previously selected person.
     */
    protected void assertSelectedCardDeselected() {
        assertFalse(getPersonListPanel().isAnyCardSelected());
    }

    /**
     * Asserts that only the person card at {@code expectedSelectedCardIndex} is selected.
     * @see PersonListPanelHandle#isSelectedPersonCardChanged()
     */
    protected void assertSelectedPersonCardChanged(Index expectedSelectedCardIndex) {
        getPersonListPanel().navigateToCard(getPersonListPanel().getSelectedCardIndex());
        assertEquals(expectedSelectedCardIndex.getZeroBased(), getPersonListPanel().getSelectedCardIndex());
    }

    /**
     * Asserts that only the group card at {@code expectedSelectedCardIndex} is selected.
     * @see GroupListPanelHandle#isSelectedGroupCardChanged()
     * TODO: add check to check if person list is updated with person who are related to the group
     */
    protected void assertSelectedGroupCardChanged(Index expectedSelectedCardIndex) {
        getGroupListPanel().navigateToCard(getGroupListPanel().getSelectedCardIndex());
        assertEquals(expectedSelectedCardIndex.getZeroBased(), getGroupListPanel().getSelectedCardIndex());
    }

    /**
     * Asserts that only the meeting card at {@code expectedSelectedCardIndex} is selected.
     * @see MeetingListPanelHandle#isSelectedMeetingCardChanged()
     */
    protected void assertSelectedMeetingCardChanged(Index expectedSelectedCardIndex) {
        getMeetingListPanel().navigateToCard(getMeetingListPanel().getSelectedCardIndex());
        assertEquals(expectedSelectedCardIndex.getZeroBased(), getMeetingListPanel().getSelectedCardIndex());
    }

    /**
     * Asserts that the selected card in the person list panel remain unchanged.
     * @see PersonListPanelHandle#isSelectedPersonCardChanged()
     */
    protected void assertSelectedPersonCardUnchanged() {
        assertFalse(getPersonListPanel().isSelectedPersonCardChanged());
    }

    /**
     * Asserts that the selected card in the group list panel remain unchanged.
     * @see GroupListPanelHandle#isSelectedGroupCardChanged()
     * TODO check if person list panel is updated
     */
    protected void assertSelectedGroupCardUnchanged() {
        assertFalse(getGroupListPanel().isSelectedGroupCardChanged());
    }

    /**
     * Asserts that the selected card in the group list panel remain unchanged.
     * @see GroupListPanelHandle#isSelectedGroupCardChanged()
     */
    protected void assertSelectedMeetingCardUnchanged() {
        assertFalse(getMeetingListPanel().isSelectedMeetingCardChanged());
    }

    /**
     * Asserts that the command box's shows the default style.
     */
    protected void assertCommandBoxShowsDefaultStyle() {
        assertEquals(COMMAND_BOX_DEFAULT_STYLE, getCommandBox().getStyleClass());
    }

    /**
     * Asserts that the command box's shows the error style.
     */
    protected void assertCommandBoxShowsErrorStyle() {
        assertEquals(COMMAND_BOX_ERROR_STYLE, getCommandBox().getStyleClass());
    }

    /**
     * Asserts that the entire status bar remains the same.
     */
    protected void assertStatusBarUnchanged() {
        StatusBarFooterHandle handle = getStatusBarFooter();
        assertFalse(handle.isSaveLocationChanged());
        assertFalse(handle.isSyncStatusChanged());
    }

    /**
     * Asserts that only the sync status in the status bar was changed to the timing of
     * {@code ClockRule#getInjectedClock()}, while the save location remains the same.
     */
    protected void assertStatusBarUnchangedExceptSyncStatus() {
        StatusBarFooterHandle handle = getStatusBarFooter();
        String timestamp = new Date(clockRule.getInjectedClock().millis()).toString();
        String expectedSyncStatus = String.format(SYNC_STATUS_UPDATED, timestamp);
        assertEquals(expectedSyncStatus, handle.getSyncStatus());
        assertFalse(handle.isSaveLocationChanged());
    }

    /**
     * Asserts that the starting state of the application is correct.
     */
    private void assertApplicationStartingStateIsCorrect() {
        assertEquals("", getCommandBox().getInput());
        assertEquals("", getResultDisplay().getText());
        assertListMatching(getPersonListPanel(), getModel().getFilteredPersonList());
        assertEquals(Paths.get(".").resolve(testApp.getStorageSaveLocation()).toString(),
                getStatusBarFooter().getSaveLocation());
        assertEquals(SYNC_STATUS_INITIAL, getStatusBarFooter().getSyncStatus());
    }

    /**
     * Returns a defensive copy of the current model.
     */
    protected Model getModel() {
        return testApp.getModel();
    }
}
