package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.After;
import org.junit.Test;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.model.Model;


public class ExportCommandSystemTest extends AddressBookSystemTest {

    private static final String EXPORT_FILE = "ExportData.xml";
    private static final Path EXPORT_PATH = Paths.get(EXPORT_FILE);

    @Test
    public void export() throws IOException {
        String command;
        String failedMsg;
        Model expectedModel = getModel();

        /* Case: export data to EXPORT_FILE with trailing whitespace-> export successful */
        command = ExportCommand.COMMAND_WORD + " " + PREFIX_PATH + " " + EXPORT_FILE + "   ";

        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
        assertFileExist(EXPORT_FILE);
        assertExportContentCorrect(EXPORT_PATH);

        deleteFileIfExists(EXPORT_PATH);

        /* Case: export data to EXPORT_FILE -> export successful */
        command = ExportCommand.COMMAND_WORD + " " + PREFIX_PATH + " " + EXPORT_FILE;

        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
        assertFileExist(EXPORT_FILE);
        assertExportContentCorrect(EXPORT_PATH);

        deleteFileIfExists(EXPORT_PATH);

        /* Case: export data without indicating filename -> rejected */
        command = ExportCommand.COMMAND_WORD;
        failedMsg = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE);
        assertCommandFailure(command, failedMsg);

        /* Case: export data without prefix -> rejected */
        command = ExportCommand.COMMAND_WORD + " " + EXPORT_FILE;
        failedMsg = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE);
        assertCommandFailure(command, failedMsg);

    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code ExportCommand#MESSAGE_EXPORT_SUCCESS} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = ExportCommand.MESSAGE_EXPORT_SUCCESS;

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }

    @After
    /**
     * Clean up file created in event of assertion failure.
     */
    public void cleanUp() {
        deleteFileIfExists(EXPORT_PATH);
    }

    /**
     * Deletes the file at {@code filePath} if it exists.
     */
    private void deleteFileIfExists(Path filePath) {
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException ioe) {
            throw new AssertionError(ioe);
        }
    }

    /**
     * Assert if the Exported File exists.
     *
     * @param filename filename
     */
    private void assertFileExist(String filename) {
        File tmpFile = new File(filename);
        assertTrue(tmpFile.exists());
    }

    /**
     * Assert if the exported address book is the same as the original saved address book by checking
     * its content.
     *
     * @param path path to the exported file
     * @throws IOException
     */
    public void assertExportContentCorrect(Path path) throws IOException {
        Path original = getDataFileLocation();
        Path export = path;
        final long size = Files.size(export);

        if (size < 4096) {
            byte[] f1 = Files.readAllBytes(original);
            byte[] f2 = Files.readAllBytes(export);
            boolean sameSize = Arrays.equals(f1, f2);
            assertTrue(sameSize);
        }

        InputStream is1 = Files.newInputStream(original);
        InputStream is2 = Files.newInputStream(export);

        int i;
        int j;
        byte[] buffer1 = new byte[1024];
        byte[] buffer2 = new byte[1024];

        do {
            i = is1.read(buffer1);
            j = is2.read(buffer2);

            assertTrue(Arrays.equals(buffer1, buffer2));
        } while ((i == j) && (i != -1));
    }
}
