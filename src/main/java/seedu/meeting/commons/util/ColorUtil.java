package seedu.meeting.commons.util;

import static java.util.Objects.requireNonNull;

import java.util.Random;

import javafx.scene.paint.Color;
import seedu.meeting.commons.exceptions.IllegalValueException;

/**
 * Utility to parse color for UI components
 */
public class ColorUtil {

    public static final int MAX_COLOR_VALUE = 128;

    public static final String PARSE_FAILURE_MESSAGE =
        "String must be in format (r,g,b) and each value should be between 0 to " + MAX_COLOR_VALUE;

    /**
     * Parses color from String. The format of {@code color} must be in "r,g,b" format.
     */
    public static Color parseColor(String color) throws IllegalValueException {
        requireNonNull(color);
        String[] rgbString = color.split(",");
        int red = Integer.parseInt(rgbString[0]);
        int green = Integer.parseInt(rgbString[1]);
        int blue = Integer.parseInt(rgbString[2]);

        if (red > MAX_COLOR_VALUE || red < 0 || green > MAX_COLOR_VALUE || green < 0
            || blue > MAX_COLOR_VALUE || blue < 0) {
            throw new IllegalValueException(PARSE_FAILURE_MESSAGE);
        }

        return Color.rgb(red, green, blue);
    }

    /**
     * Generates a random color and returns a String in "r,g,b" format.
     */
    public static String getRandomColorString() {
        Random random = new Random();
        int red = random.nextInt(MAX_COLOR_VALUE + 1);
        int green = random.nextInt(MAX_COLOR_VALUE + 1);
        int blue = random.nextInt(MAX_COLOR_VALUE + 1);
        return red + "," + green + "," + blue;
    }

    /**
     * Parses rgb values to colorString.
     * @throws IllegalValueException if any one of the parameters do not lie within the range of 0 and 255
     */
    public static String parseColorString(int red, int green, int blue) throws IllegalValueException {
        if (red < 0 || red > MAX_COLOR_VALUE || green < 0 || green > MAX_COLOR_VALUE
            || blue < 0 || blue > MAX_COLOR_VALUE) {
            throw new IllegalValueException(PARSE_FAILURE_MESSAGE);
        }

        return red + "," + green + "," + blue;
    }
}
