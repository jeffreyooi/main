package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.PersonPropertyComparator;

/**
 * Sorts the displayed person list based on the property given by their natural ordering.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts the displayed person list based on the property given by their natural ordering.\n"
            + "Parameters: [name/phone/email/address]\n"
            + "Example: " + COMMAND_WORD + " name";

    // private final Comparator<Person> personPropertyComparator;
    private final PersonPropertyComparator personPropertyComparator;

    public SortCommand(PersonPropertyComparator personPropertyComparator) {
        this.personPropertyComparator = personPropertyComparator;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateSortedPersonList(personPropertyComparator);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_SORTED_OVERVIEW, personPropertyComparator.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && personPropertyComparator.equals(((SortCommand) other).personPropertyComparator)); // state check
    }
}
