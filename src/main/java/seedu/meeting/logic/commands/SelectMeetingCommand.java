package seedu.meeting.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.meeting.commons.core.EventsCenter;
import seedu.meeting.commons.core.Messages;
import seedu.meeting.commons.core.index.Index;
import seedu.meeting.commons.events.ui.JumpToMeetingListRequestEvent;
import seedu.meeting.logic.CommandHistory;
import seedu.meeting.logic.commands.exceptions.CommandException;
import seedu.meeting.model.Model;
import seedu.meeting.model.meeting.Meeting;

/**
 * Selects meeting in MeetingBook whose index matches the id of the meeting card.
 */
public class SelectMeetingCommand extends SelectCommand<Meeting> {

    public SelectMeetingCommand(Index index) {
        super(index);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Meeting> filteredMeetingList = model.getFilteredMeetingList();
        if (targetIndex.getZeroBased() >= filteredMeetingList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEETING_DISPLAYED_INDEX);
        }
        EventsCenter.getInstance().post(new JumpToMeetingListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_MEETING_SUCCESS, targetIndex.getOneBased()));
    }
}
