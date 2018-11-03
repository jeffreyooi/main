package seedu.meeting.logic.commands;

import static seedu.meeting.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.meeting.logic.parser.CliSyntax.PREFIX_MEETING;
import static seedu.meeting.logic.parser.CliSyntax.PREFIX_PERSON;

import seedu.meeting.commons.core.index.Index;

/**
 * Selects a person identified using it's displayed index from the MeetingBook.
 */
public abstract class SelectCommand<T> extends Command {

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the person identified by the index number used in the displayed person list.\n"
            + "Parameters: [" + PREFIX_GROUP + " or " + PREFIX_MEETING + " or "
                            + PREFIX_PERSON + "]INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " g/1";

    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "Selected Person: %1$s";
    public static final String MESSAGE_SELECT_GROUP_SUCCESS = "Selected Group: %1$s";
    public static final String MESSAGE_SELECT_MEETING_SUCCESS = "Selected Meeting: %1$s";

    protected final Index targetIndex;

    public SelectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCommand // instanceof handles nulls
                && targetIndex.equals(((SelectCommand) other).targetIndex)); // state check
    }
}
