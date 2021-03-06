package seedu.meeting.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.meeting.commons.exceptions.DataConversionException;
import seedu.meeting.model.MeetingBook;
import seedu.meeting.model.ReadOnlyMeetingBook;

/**
 * Represents a storage for {@link MeetingBook}.
 */
public interface MeetingBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getMeetingBookFilePath();

    /**
     * Returns MeetingBook data as a {@link ReadOnlyMeetingBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyMeetingBook> readMeetingBook() throws DataConversionException, IOException;

    /**
     * @see #getMeetingBookFilePath()
     */
    Optional<ReadOnlyMeetingBook> readMeetingBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyMeetingBook} to the storage.
     * @param meetingBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveMeetingBook(ReadOnlyMeetingBook meetingBook) throws IOException;

    /**
     * @see #saveMeetingBook(ReadOnlyMeetingBook)
     */
    void saveMeetingBook(ReadOnlyMeetingBook meetingBook, Path filePath) throws IOException;

    /**
     * Delete MeetingBook in the storage.
     * @throws IOException if file is unable to be written.
     */
    void deleteMeetingBook(Path filePath) throws IOException;
}
