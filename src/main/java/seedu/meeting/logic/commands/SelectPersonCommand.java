package seedu.meeting.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.meeting.commons.core.EventsCenter;
import seedu.meeting.commons.core.Messages;
import seedu.meeting.commons.core.index.Index;
import seedu.meeting.commons.events.ui.JumpToListRequestEvent;
import seedu.meeting.logic.CommandHistory;
import seedu.meeting.logic.commands.exceptions.CommandException;
import seedu.meeting.model.Model;
import seedu.meeting.model.person.Person;

/**
 * Selects person in MeetingBook whose index matches the id of the person card.
 */
public class SelectPersonCommand extends SelectCommand<Person> {

    public SelectPersonCommand(Index index) {
        super(index);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Person> filteredPersonList = model.getFilteredPersonList();
        if (targetIndex.getZeroBased() >= filteredPersonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_PERSON_SUCCESS, targetIndex.getOneBased()));
    }
}
