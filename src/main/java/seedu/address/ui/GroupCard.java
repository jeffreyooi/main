package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.tag.Tag;

public class GroupCard extends UiPart<Region> {

    private static final String FXML = "GroupListCard.fxml";

    public final Tag group;

    @FXML
    private Label id;

    @FXML
    private Label groupName;

    public GroupCard(Tag group, int displayedIndex) {
        super(FXML);
        this.group = group;
        id.setText(displayedIndex + ". ");
        groupName.setText(group.tagName);
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
