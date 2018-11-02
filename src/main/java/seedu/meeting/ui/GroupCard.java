package seedu.meeting.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import seedu.meeting.commons.core.LogsCenter;
import seedu.meeting.commons.exceptions.IllegalValueException;
import seedu.meeting.commons.util.ColorUtil;
import seedu.meeting.model.group.Group;
import seedu.meeting.model.meeting.Meeting;
import seedu.meeting.model.shared.Description;

/**
 * An UI component that displays information of a {@code Group}
 * {@author jeffreyooi}
 */
public class GroupCard extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(GroupCard.class);
    private static final String FXML = "GroupListCard.fxml";

    public final Group group;

    @FXML
    private Pane colorPane;

    @FXML
    private HBox groupCardPane;

    @FXML
    private Label id;

    @FXML
    private Label groupTitle;

    @FXML
    private Label groupDescription;

    @FXML
    private Label groupMeeting;

    @FXML
    private Label memberCount;

    public GroupCard(Group group, int displayedIndex) {
        super(FXML);
        this.group = group;
        id.setText(displayedIndex + ". ");
        groupTitle.setText(group.getTitle().fullTitle);
        Description description = group.getDescription();
        String descriptionString = description != null ? description.statement : "";
        groupDescription.setText(descriptionString);
        Meeting meeting = group.getMeeting();
        String meetingString = meeting != null ? meeting.getTitle().fullTitle : "";
        groupMeeting.setText(meetingString);
        int membersCount = group.getMembersView().size();
        memberCount.setText(String.format("%d", membersCount));
        try {
            colorPane.setBackground(new Background(new BackgroundFill(ColorUtil.parseColor(group.getColorString()),
                new CornerRadii(4), Insets.EMPTY)));
        } catch (IllegalValueException ive) {
            logger.warning("Failed to parse color: " + group.getColorString() + "\n" + ive);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof GroupCard)) {
            return false;
        }

        GroupCard card = (GroupCard) other;
        return id.getText().equals(card.id.getText())
                && group.equals(card.group);
    }
}
