package seedu.address.model.group;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.Month;
import java.util.Calendar;
import java.util.GregorianCalendar;

/* @@author Pakorn */
/**
 * TimeStamp represents a specific timing on the calendar.
 */
public class TimeStamp implements Comparable<TimeStamp> {
    public static final String DATE_SPLIT_REGEX = "[:@-]";

    public static final int SPLITTED_YEAR_INDEX = 2;
    public static final int SPLITTED_MONTH_INDEX = 1;
    public static final int SPLITTED_DAY_INDEX = 0;
    public static final int SPLITTED_HOUR_INDEX = 3;
    public static final int SPLITTED_MINUTE_INDEX = 4;
    public static final int EXPECTED_SPLIITTED_LENGTH = 5;

    public static final String MESSAGE_TIMESTAMP_CONSTRAINT = "TimeStamp must follow the following constraints:\n"
            + "1. TimeStamp String must be of the format DD-MM-YYYY@HH:MM\n"
            + "2. Date must exists in the calendar\n"
            + "3. Time of day must be between 0 hour 0 minute to 23 hours 59 minutes";
    public static final String MESSAGE_NULL_TIMESTAMP = "TimeStamp String must not be null";

    private static final int MINIMUM_YEAR = 0;
    private static final int MINIMUM_DAY_OF_MONTH = 1;
    private static final int MINIMUM_HOUR_OF_DAY = 0;
    private static final int MAXIMUM_HOUR_OF_DAY = 23;
    private static final int MINIMUM_MINUTE_OF_HOUR = 0;
    private static final int MAXIMUM_MINUTE_OF_HOUR = 59;

    private Calendar value;

    public TimeStamp(Integer year, Month month, Integer date, Integer hour, Integer minute) {
        requireAllNonNull(year, month, date, hour, minute);
        EnhancedMonth enhancedMonth = new EnhancedMonth(month);
        checkArgument(isValidArgument(year, enhancedMonth, date, hour, minute),
                MESSAGE_TIMESTAMP_CONSTRAINT);
        value = new GregorianCalendar();
        value.set(year, enhancedMonth.getMonthIndex().getZeroBased(), date, hour, minute);

    }

    /**
     * Returns true if the specified arguments could be combined together to create a valid {@code TimeStamp}.
     */
    public static boolean isValidArgument(
            Integer year, EnhancedMonth month, Integer date, Integer hour, Integer minute) {
        if (year < MINIMUM_YEAR) {
            return false;
        }
        if (date < MINIMUM_DAY_OF_MONTH || date > month.getLength(year)) {
            return false;
        }
        if (hour < MINIMUM_HOUR_OF_DAY || hour > MAXIMUM_HOUR_OF_DAY) {
            return false;
        }
        return minute >= MINIMUM_MINUTE_OF_HOUR && minute <= MAXIMUM_MINUTE_OF_HOUR;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof TimeStamp)
                && value.equals(((TimeStamp) other).value);
    }

    @Override
    public int compareTo(TimeStamp other) {
        return value.compareTo(other.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return (value.get(Calendar.DATE)
                + "-" + value.get(Calendar.MONTH)
                + "-" + value.get(Calendar.YEAR)
                + "@" + value.get(Calendar.HOUR_OF_DAY)
                + ":" + value.get(Calendar.MINUTE)).trim();
    }
}
