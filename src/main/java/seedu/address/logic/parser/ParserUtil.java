package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import static seedu.address.model.group.TimeStamp.DATE_SPLIT_REGEX;
import static seedu.address.model.group.TimeStamp.EXPECTED_SPLIITTED_LENGTH;
import static seedu.address.model.group.TimeStamp.MESSAGE_TIMESTAMP_CONSTRAINT;
import static seedu.address.model.group.TimeStamp.SPLITTED_DAY_INDEX;
import static seedu.address.model.group.TimeStamp.SPLITTED_HOUR_INDEX;
import static seedu.address.model.group.TimeStamp.SPLITTED_MINUTE_INDEX;
import static seedu.address.model.group.TimeStamp.SPLITTED_MONTH_INDEX;
import static seedu.address.model.group.TimeStamp.SPLITTED_YEAR_INDEX;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Month;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.Date;
import seedu.address.model.group.Description;
import seedu.address.model.group.EnhancedMonth;
import seedu.address.model.group.Place;
import seedu.address.model.group.TimeStamp;
import seedu.address.model.group.Title;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonPropertyComparator;
import seedu.address.model.person.Phone;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String groupTag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code groupTag} is invalid.
     */
    public static Tag parseGroupTag(String groupTag) throws ParseException {
        requireNonNull(groupTag);
        String trimmedGroupTag = groupTag.trim();
        if (!Tag.isValidTagName(trimmedGroupTag)) {
            throw new ParseException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
        return new Tag(trimmedGroupTag);
    }

    /**
     * Parses a {@code Collection<String> groupTags} into a {@code Set<Tag>}
     */
    public static Set<Tag> parseGroupTags(Collection<String> groupTags) throws ParseException {
        if (groupTags == null || groupTags.isEmpty()) {
            return null;
        }
        final Set<Tag> groupTagSet = new HashSet<>();
        for (String groupTagName : groupTags) {
            groupTagSet.add(parseGroupTag(groupTagName));
        }
        return groupTagSet;
    }

    /* @@author Pakorn */
    /**
     * Parse a {@code String} representation of a timestamp into a {@code TimeStamp} object.
     */
    public static TimeStamp parseTimeStamp(String timeStamp) throws ParseException {
        requireNonNull(timeStamp);
        String[] splitted = timeStamp.split(DATE_SPLIT_REGEX);
        if (splitted.length != EXPECTED_SPLIITTED_LENGTH) {
            System.out.println("1");
            throw new ParseException(MESSAGE_TIMESTAMP_CONSTRAINT);
        }
        if (!TimeStamp.isValidArgument(Integer.parseInt(splitted[SPLITTED_YEAR_INDEX]),
                EnhancedMonth.fromMonthIndex(Integer.parseInt(splitted[SPLITTED_MONTH_INDEX])),
                Integer.parseInt(splitted[SPLITTED_DAY_INDEX]),
                Integer.parseInt(splitted[SPLITTED_HOUR_INDEX]),
                Integer.parseInt(splitted[SPLITTED_MINUTE_INDEX]))) {
            System.out.println("2");
            throw new ParseException(MESSAGE_TIMESTAMP_CONSTRAINT);
        }
        return new TimeStamp(Integer.parseInt(splitted[SPLITTED_YEAR_INDEX]),
                Month.of(Integer.parseInt(splitted[SPLITTED_MONTH_INDEX])),
                Integer.parseInt(splitted[SPLITTED_DAY_INDEX]),
                Integer.parseInt(splitted[SPLITTED_HOUR_INDEX]),
                Integer.parseInt(splitted[SPLITTED_MINUTE_INDEX]));
    }
    /* @@author */

    /**
     * Parses a {@code String title} into a {@code Title}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code title} is invalid.
     */
    public static Title parseTitle(String title) throws ParseException {
        requireNonNull(title);
        String trimmedTitle = title.trim();
        if (!Title.isValidTitle(trimmedTitle)) {
            throw new ParseException(Title.MESSAGE_TITLE_CONSTRAINTS);
        }
        return new Title(trimmedTitle);
    }

    /**
     * Parses a {@code String place} into a {@code Place}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code place} is invalid.
     */
    public static Place parsePlace(String place) throws ParseException {
        requireNonNull(place);
        String trimmedPlace = place.trim();
        if (!Place.isValidPlace(trimmedPlace)) {
            throw new ParseException(Place.MESSAGE_PLACE_CONSTRAINTS);
        }
        return new Place(trimmedPlace);
    }

    /**
     * Parses a {@code String date} into a {@code Date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static Date parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!Date.isValidDate(trimmedDate)) {
            throw new ParseException(Date.MESSAGE_DATE_CONSTRAINTS);
        }
        return new Date(trimmedDate);
    }

    /**
     * Parses a {@code String description} into a {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static Description parseDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!Description.isValidDescription(trimmedDescription)) {
            throw new ParseException(Description.MESSAGE_DESCRIPTION_CONSTRAINTS);
        }
        return new Description(trimmedDescription);
    }

    /**
     * Parses {@code Iterable<Person> members} into a {@code UniquePersonList}.
     */
    public static UniquePersonList parseMember(Iterable<Person> members) throws ParseException {
        requireNonNull(members);
        final UniquePersonList parseList = new UniquePersonList();
        members.forEach(parseList::add);

        return parseList;
    }

    /**
     * Parses a {@code String personProperty} into a {@code PersonPropertyComparator}.
     * Leading and trailing whitespaces will be trimmed.
     * @param personProperty the property of person.
     * @return the PersonPropertyComparator representing the comparator for that property.
     * @throws ParseException if the given {@code personProperty} is invalid.
     */
    public static PersonPropertyComparator parsePersonPropertyComparator(String personProperty) throws ParseException {
        requireNonNull(personProperty);
        String trimmedPersonProperty = personProperty.trim();
        try {
            return PersonPropertyComparator.getPersonPropertyComparator(trimmedPersonProperty);
        } catch (IllegalArgumentException e) {
            throw new ParseException(PersonPropertyComparator.MESSAGE_PERSON_PROPERTY_CONSTRAINTS);
        }
    }

    /**
     * Parse {@code String filepath} into {@code Path}.
     */
    public static Path parsePath(String filepath) throws ParseException {
        requireNonNull(filepath);
        String trimmed = filepath.trim();
        Path path = Paths.get(trimmed);
        return path;
    }
}
