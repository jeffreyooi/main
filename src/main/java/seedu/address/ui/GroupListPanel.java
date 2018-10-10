package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.GroupPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.tag.Tag;

/**
 * Panel containing the list of groups.
 */
public class GroupListPanel extends UiPart<Region> {
    private static final String FXML = "GroupListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(GroupListPanel.class);

    @FXML
    private ListView<Tag> groupListView;

    public GroupListPanel(ObservableList<Tag> groupList) {
        super(FXML);
        setConnections(groupList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Tag> groupList) {
        groupListView.setItems(groupList);
        groupListView.setCellFactory(listView -> new GroupListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        groupListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in group list panel changed to : '" + newValue + "'");
                        raise(new GroupPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code GroupCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            groupListView.scrollTo(index);
            groupListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Group} using a {@code GroupCard}
     */
    class GroupListViewCell extends ListCell<Tag> {
        @Override
        protected void updateItem(Tag tag, boolean empty) {
            super.updateItem(tag, empty);

            if (empty || tag == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new GroupCard(tag, getIndex() + 1).getRoot());
            }
        }
    }
}
