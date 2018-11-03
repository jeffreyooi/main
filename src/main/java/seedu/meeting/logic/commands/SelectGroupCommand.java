package seedu.meeting.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.List;

import seedu.meeting.commons.core.EventsCenter;
import seedu.meeting.commons.core.Messages;
import seedu.meeting.commons.core.index.Index;
import seedu.meeting.commons.events.ui.JumpToGroupListRequestEvent;
import seedu.meeting.logic.CommandHistory;
import seedu.meeting.logic.commands.exceptions.CommandException;
import seedu.meeting.model.Model;
import seedu.meeting.model.group.Group;
import seedu.meeting.model.group.util.GroupContainsMeetingPredicate;
import seedu.meeting.model.group.util.GroupContainsPersonPredicate;

/**
 * Selects group in MeetingBook whose index matches the id of the group card.
 */
public class SelectGroupCommand extends SelectCommand<Group> {

    public SelectGroupCommand(Index index) {
        super(index);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Group> filteredGroupList = model.getFilteredGroupList();
        if (targetIndex.getZeroBased() >= filteredGroupList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
        }
        EventsCenter.getInstance().post(new JumpToGroupListRequestEvent(targetIndex));
        Group group = filteredGroupList.get(targetIndex.getZeroBased());
        model.updateFilteredPersonList(new GroupContainsPersonPredicate(Arrays.asList(group)));
        model.updateFilteredMeetingList(new GroupContainsMeetingPredicate(Arrays.asList(group)));
        return new CommandResult(String.format(MESSAGE_SELECT_GROUP_SUCCESS, targetIndex.getOneBased()));
    }
}
