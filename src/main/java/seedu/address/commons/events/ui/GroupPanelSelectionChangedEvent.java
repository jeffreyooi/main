package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.tag.Tag;

/**
 * Represents a selection change in the Group List Panel
 */
public class GroupPanelSelectionChangedEvent extends BaseEvent {

    private final Tag newSelection;

    public GroupPanelSelectionChangedEvent(Tag newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Tag getNewSelection() {
        return newSelection;
    }
}
