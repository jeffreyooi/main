package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH;

import java.nio.file.Path;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Export existing address book to user-defined path
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String MESSAGE_EXPORT_SUCCESS = "Export process has been successful.";
    public static final String MISSING_PATH = "";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Export the address book to specific filepath. "
            + "Parameters: "
            + PREFIX_PATH + "FilePath\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PATH + "backup";

    private Path filepath;

    public ExportCommand(Path filePath) {
        this.filepath = filePath;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        model.exportAddressBook(this.filepath);
        return new CommandResult(MESSAGE_EXPORT_SUCCESS);
    }

}
