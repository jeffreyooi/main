package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonPropertyComparator;
import seedu.address.model.tag.Tag;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void updatePerson(Person target, Person editedPerson);

    /**
     * Add a group tag {@code newGroup} to the given person {@code target}.
     * No action is required if the given person already exists in the
     * group.
     *
     * @param target New participant of the group
     * @param newGroup Tag of the group that given person is added in
     */
    void addGroup(Person target, Tag newGroup);

    /**
     * Remove a group tag {@code oldGroup} from the given person {@code target}.
     * Report error message if the given person is not previously in the
     * group.
     *
     * @param target Existing participant of the group
     * @param oldGroup Tag of the group that the given person is currently in
     */
    void removeGroup(Person target, Tag oldGroup);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);


    /** Returns an unmodifiable view of the filtered group list */
    ObservableList<Tag> getFilteredGroupList();

    /**
     * Updates the filter of the filtered group list to filter by the given {@code predicate}
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredGroupList(Predicate<Tag> predicate);

    /**
     * @return An unmodifiable view of the sorted person list
     */
    ObservableList<Person> getSortedPersonList();

    /**
     * Updates the sorting of the sorted person list to sort by the given {@code comparator}.
     * @param personPropertyComparator The comparator to sort the list by.
     */
    void updateSortedPersonList(PersonPropertyComparator personPropertyComparator);

    /**
     * Returns true if the model has previous address book states to restore.
     */
    boolean canUndoAddressBook();

    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoAddressBook();

    /**
     * Restores the model's address book to its previous state.
     */
    void undoAddressBook();

    /**
     * Restores the model's address book to its previously undone state.
     */
    void redoAddressBook();

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitAddressBook();

    /**
     * Export the current address book.
     */
    void exportAddressBook(Path filepath);
}
