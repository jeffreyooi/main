package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.meeting.model.meeting.Meeting;

// @@author jeffreyooi
/**
 * Provides a handle for {@code MeetingListPanel} containing the list of {@code MeetingCard}
 */
public class MeetingListPanelHandle extends NodeHandle<ListView<Meeting>> {
    public static final String MEETING_LIST_VIEW_ID = "#meetingListView";

    private static final String CARD_PANE_ID = "#meetingCardPane";

    private Optional<Meeting> lastRememberedSelectedMeetingCard;

    public MeetingListPanelHandle(ListView<Meeting> meetingListPanelNode) {
        super(meetingListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code MeetingCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public MeetingCardHandle getHandleToSelectedCard() {
        List<Meeting> selectedMeetingList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedMeetingList.size() != 1) {
            throw new AssertionError("Meeting list size expected 1.");
        }

        return getAllCardNodes().stream()
            .map(MeetingCardHandle::new)
            .filter(handle -> handle.equals(selectedMeetingList.get(0)))
            .findFirst()
            .orElseThrow(IllegalStateException::new);
    }


    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Navigates the listview to display {@code meeting}.
     */
    public void navigateToCard(Meeting meeting) {
        if (!getRootNode().getItems().contains(meeting)) {
            throw new IllegalArgumentException("Group does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(meeting);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Navigates the listview to {@code index}.
     */
    public void navigateToCard(int index) {
        if (index < 0 || index >= getRootNode().getItems().size()) {
            throw new IllegalArgumentException("Index is out of bounds.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(index);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the meeting card handle of a meeting associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public MeetingCardHandle getMeetingCardHandle(int index) {
        return getAllCardNodes().stream()
            .map(MeetingCardHandle::new)
            .filter(handle -> handle.equals(getMeeting(index)))
            .findFirst()
            .orElseThrow(IllegalStateException::new);
    }

    private Meeting getMeeting(int index) {
        return getRootNode().getItems().get(index);
    }

    /**
     * Returns all card nodes in the scene graph.
     * Card nodes that are visible in the listview are definitely in the scene graph, while some nodes that are not
     * visible in the listview may also be in the scene graph.
     */
    private Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    /**
     * Remembers the selected {@code MeetingCard} in the list.
     */
    public void rememberSelectedMeetingCard() {
        List<Meeting> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedMeetingCard = Optional.empty();
        } else {
            lastRememberedSelectedMeetingCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code MeetingCard} is different from the value remembered by the most recent
     * {@code rememberSelectedMeetingCard()} call.
     */
    public boolean isSelectedMeetingCardChanged() {
        List<Meeting> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedMeetingCard.isPresent();
        } else {
            return !lastRememberedSelectedMeetingCard.isPresent()
                || !lastRememberedSelectedMeetingCard.get().equals(selectedItems.get(0));
        }
    }
}
