package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;
import seedu.address.model.person.PersonPropertyComparator;

public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "name phone",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "phone address",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "something else",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

    }


    @Test
    public void parse_validArgs_returnsSortCommand() {
        // no leading and trailing whitespaces, lower case
        SortCommand expectedSortByNameCommand =
                new SortCommand(PersonPropertyComparator.getPersonPropertyComparator("name"));
        SortCommand expectedSortByPhoneCommand =
                new SortCommand(PersonPropertyComparator.getPersonPropertyComparator("phone"));
        SortCommand expectedSortByEmailCommand =
                new SortCommand(PersonPropertyComparator.getPersonPropertyComparator("email"));
        SortCommand expectedSortByAddressCommand =
                new SortCommand(PersonPropertyComparator.getPersonPropertyComparator("address"));

        assertParseSuccess(parser, "name", expectedSortByNameCommand);
        assertParseSuccess(parser, "phone", expectedSortByPhoneCommand);
        assertParseSuccess(parser, "email", expectedSortByEmailCommand);
        assertParseSuccess(parser, "address", expectedSortByAddressCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "\n \t name", expectedSortByNameCommand);
        assertParseSuccess(parser, " \t \n phone\t \n", expectedSortByPhoneCommand);
        assertParseSuccess(parser, "\t email \n", expectedSortByEmailCommand);
        assertParseSuccess(parser, " \naddress ", expectedSortByAddressCommand);

        // case insensitive
        assertParseSuccess(parser, "NAME", expectedSortByNameCommand);
        assertParseSuccess(parser, "PHONE", expectedSortByPhoneCommand);
        assertParseSuccess(parser, "EMAIL", expectedSortByEmailCommand);
        assertParseSuccess(parser, "ADDRESS", expectedSortByAddressCommand);

        assertParseSuccess(parser, "nAmE", expectedSortByNameCommand);
        assertParseSuccess(parser, "PhOne", expectedSortByPhoneCommand);
        assertParseSuccess(parser, "EmAiL", expectedSortByEmailCommand);
        assertParseSuccess(parser, "addREsS", expectedSortByAddressCommand);
    }
}
