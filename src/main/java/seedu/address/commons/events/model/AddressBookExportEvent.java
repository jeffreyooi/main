package seedu.address.commons.events.model;

import java.nio.file.Path;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyAddressBook;


/** Indicates the AddressBook in the model has triggered for export*/
public class AddressBookExportEvent extends BaseEvent {

    public final ReadOnlyAddressBook data;
    public final Path path;

    public AddressBookExportEvent(ReadOnlyAddressBook data, Path path) {
        this.data = data;
        this.path = path;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getPersonList().size();
    }
}
