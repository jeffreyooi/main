package seedu.meeting.logic.parser;

import static seedu.meeting.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.meeting.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.meeting.logic.parser.CliSyntax.PREFIX_MEETING;
import static seedu.meeting.logic.parser.CliSyntax.PREFIX_PERSON;

import seedu.meeting.commons.core.index.Index;
import seedu.meeting.logic.commands.SelectCommand;
import seedu.meeting.logic.commands.SelectGroupCommand;
import seedu.meeting.logic.commands.SelectMeetingCommand;
import seedu.meeting.logic.commands.SelectPersonCommand;
import seedu.meeting.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectCommand object
 */
public class SelectCommandParser implements Parser<SelectCommand> {

    /**
     * The type of SelectCommand to create for execution.
     */
    private enum SelectCommandType {
        PERSON, GROUP, MEETING
    }

    /**
     * Parses the given {@code String} of arguments in the context of the SelectCommand
     * and returns an SelectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_GROUP, PREFIX_PERSON, PREFIX_MEETING);

        if (!argMultimap.areAnyPrefixesPresent(PREFIX_GROUP, PREFIX_PERSON, PREFIX_MEETING)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }

        try {
            SelectCommandType selectCommandType = parseSelectCommandType(argMultimap);
            Index index = parseSelectIndex(argMultimap, selectCommandType);
            return createSelectCommand(selectCommandType, index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Creates a {@code SelectCommand} from type specified by user
     * @throws ParseException if type does not exist.
     */
    private SelectCommand createSelectCommand(SelectCommandType selectCommandType, Index index) throws ParseException {
        switch (selectCommandType) {
        case GROUP:
            return new SelectGroupCommand(index);
        case MEETING:
            return new SelectMeetingCommand(index);
        case PERSON:
            return new SelectPersonCommand(index);
        default:
            throw new ParseException("Unknown option.");
        }
    }

    /**
     * Returns the {@code SelectCommand.SelectCommandType} from user input.
     * @throws ParseException if the prefix does not exist
     */
    private SelectCommandType parseSelectCommandType(ArgumentMultimap argumentMultimap)
        throws ParseException {

        if (argumentMultimap.getValue(PREFIX_GROUP).isPresent()) {
            return SelectCommandType.GROUP;
        } else if (argumentMultimap.getValue(PREFIX_MEETING).isPresent()) {
            return SelectCommandType.MEETING;
        } else if (argumentMultimap.getValue(PREFIX_PERSON).isPresent()) {
            return SelectCommandType.PERSON;
        } else {
            throw new ParseException("Unknown prefix");
        }
    }

    /**
     * Returns the index from user input based on the {@code selectType}
     * @throws ParseException if the prefix does not exist
     */
    private Index parseSelectIndex(ArgumentMultimap argumentMultimap, SelectCommandType selectType)
        throws ParseException {
        switch (selectType) {
        case GROUP:
            return ParserUtil.parseIndex(argumentMultimap.getValue(PREFIX_GROUP).get());
        case MEETING:
            return ParserUtil.parseIndex(argumentMultimap.getValue(PREFIX_MEETING).get());
        case PERSON:
            return ParserUtil.parseIndex(argumentMultimap.getValue(PREFIX_PERSON).get());
        default:
            throw new ParseException("Unknown prefix");
        }
    }
}
