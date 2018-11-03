package seedu.meeting.testutil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.meeting.commons.core.index.Index;
import seedu.meeting.model.Model;
import seedu.meeting.model.person.Person;

/**
 * A utility class for test cases.
 */
public class TestUtil {

    /**
     * Folder used for temp files created during testing. Ignored by Git.
     */
    private static final Path SANDBOX_FOLDER = Paths.get("src", "test", "data", "sandbox");

    /**
     * Appends {@code fileName} to the sandbox folder path and returns the resulting path.
     * Creates the sandbox folder if it doesn't exist.
     */
    public static Path getFilePathInSandboxFolder(String fileName) {
        try {
            Files.createDirectories(SANDBOX_FOLDER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return SANDBOX_FOLDER.resolve(fileName);
    }

    /**
     * Returns the middle index of the person in the {@code model}'s person list.
     */
    public static Index getPersonMidIndex(Model model) {
        return Index.fromOneBased(model.getFilteredPersonList().size() / 2);
    }

    /**
     * Returns the last index of the person in the {@code model}'s person list.
     */
    public static Index getPersonLastIndex(Model model) {
        return Index.fromOneBased(model.getFilteredPersonList().size());
    }

    /**
     * Returns the person in the {@code model}'s person list at {@code index}.
     */
    public static Person getPerson(Model model, Index index) {
        return model.getFilteredPersonList().get(index.getZeroBased());
    }

    /**
     * Returns the middle index of the group in the {@code model}'s group list.
     */
    public static Index getGroupMidIndex(Model model) {
        return Index.fromOneBased(model.getFilteredGroupList().size() / 2);
    }

    /**
     * Returns the last index of the group in the {@code model}'s group list.
     */
    public static Index getGroupLastIndex(Model model) {
        return Index.fromOneBased(model.getFilteredGroupList().size());
    }

    /**
     * Returns the middle index of the meeting in the {@code model}'s meeting list.
     */
    public static Index getMeetingMidIndex(Model model) {
        return Index.fromOneBased(model.getFilteredMeetingList().size() / 2);
    }

    /**
     * Returns the last index of the meeting in the {@code model}'s meeting list.
     */
    public static Index getMeetingLastIndex(Model model) {
        return Index.fromOneBased(model.getFilteredMeetingList().size());
    }
}
