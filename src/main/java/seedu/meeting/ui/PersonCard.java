package seedu.meeting.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.meeting.commons.core.LogsCenter;
import seedu.meeting.commons.exceptions.IllegalValueException;
import seedu.meeting.commons.util.ColorUtil;
import seedu.meeting.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(PersonCard.class);
    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on MeetingBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox personCardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane groups;

    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        person.getGroups().forEach(group -> {
            Label label = new Label(group.getTitle().fullTitle);
            try {
                label.setBackground(new Background(new BackgroundFill(ColorUtil.parseColor(group.getColorString()),
                    new CornerRadii(4), Insets.EMPTY)));
            } catch (IllegalValueException ive) {
                logger.warning("Failed to parse color: " + group.getColorString() + "\n" + ive.getMessage());
            }
            groups.getChildren().add(label);
        });
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person)
                && name.getText().equals(card.name.getText())
                && phone.getText().equals(card.phone.getText())
                && address.getText().equals(card.address.getText())
                && email.getText().equals(card.email.getText());
    }
}
