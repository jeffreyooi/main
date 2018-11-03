package seedu.meeting.logic.parser;

import static seedu.meeting.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.meeting.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.meeting.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.meeting.testutil.TypicalIndexes.INDEX_FIRST_GROUP;
import static seedu.meeting.testutil.TypicalIndexes.INDEX_FIRST_MEETING;
import static seedu.meeting.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.meeting.logic.commands.SelectCommand;
import seedu.meeting.logic.commands.SelectGroupCommand;
import seedu.meeting.logic.commands.SelectMeetingCommand;
import seedu.meeting.logic.commands.SelectPersonCommand;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class SelectCommandParserTest {

    private SelectCommandParser parser = new SelectCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, " p/1", new SelectPersonCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_validArgs_returnsSelectGroupCommand() {
        assertParseSuccess(parser, " g/1", new SelectGroupCommand(INDEX_FIRST_GROUP));
    }

    @Test
    public void parse_validArgs_returnsSelectMeetingCommand() {
        assertParseSuccess(parser, " m/1", new SelectMeetingCommand(INDEX_FIRST_MEETING));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
    }
}
