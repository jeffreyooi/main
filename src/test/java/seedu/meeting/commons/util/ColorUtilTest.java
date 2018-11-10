package seedu.meeting.commons.util;

import static org.junit.Assert.assertEquals;
import static seedu.meeting.commons.util.ColorUtil.MAX_COLOR_VALUE;
import static seedu.meeting.commons.util.ColorUtil.PARSE_FAILURE_MESSAGE;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.scene.paint.Color;
import seedu.meeting.commons.exceptions.IllegalValueException;

public class ColorUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseColorString_validValues_throwsNothing() throws IllegalValueException {
        assertEquals("128,128,128", ColorUtil.parseColorString(128, 128, 128));
    }

    @Test
    public void parseColorString_invalidValues_throwsIllegalValueException() throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(PARSE_FAILURE_MESSAGE);

        // Red > 255 -> throws exception
        ColorUtil.parseColorString(MAX_COLOR_VALUE + 1, 0, 0);

        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(PARSE_FAILURE_MESSAGE);
        // Red < 128 -> throws exception
        ColorUtil.parseColorString(-1, 0, 0);

        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(PARSE_FAILURE_MESSAGE);
        // Green > 255 -> throws exception
        ColorUtil.parseColorString(0, MAX_COLOR_VALUE + 1, 0);

        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(PARSE_FAILURE_MESSAGE);
        // Green < 128 -> throws exception
        ColorUtil.parseColorString(0, -1, 0);

        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(PARSE_FAILURE_MESSAGE);
        // Blue > 255 -> throws exception
        ColorUtil.parseColorString(0, 0, MAX_COLOR_VALUE + 1);

        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(PARSE_FAILURE_MESSAGE);
        // Blue < 128 -> throws exception
        ColorUtil.parseColorString(0, 0, -1);
    }

    @Test
    public void parse_validValueRange_returnsColor() throws IllegalValueException {
        Color color = Color.rgb(128, 128, 128);

        String colorString = ColorUtil.parseColorString(128, 128, 128);
        Color parsedColor = ColorUtil.parseColor(colorString);
        assertEquals(color, parsedColor);
    }

    @Test
    public void parse_invalidValueRange_returnsColor() throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(PARSE_FAILURE_MESSAGE);
        ColorUtil.parseColor("256,0,0");
        ColorUtil.parseColor("-1,0,0");
        ColorUtil.parseColor("0,256,0");
        ColorUtil.parseColor("0,-1,0");
        ColorUtil.parseColor("0,0,256");
        ColorUtil.parseColor("0,0,-1");
    }

    @Test
    public void parse_nullValue_throwsNullPointerException() throws IllegalValueException {
        thrown.expect(NullPointerException.class);
        ColorUtil.parseColor(null);
    }
}
