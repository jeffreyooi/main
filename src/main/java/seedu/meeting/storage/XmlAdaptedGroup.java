package seedu.meeting.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.meeting.commons.exceptions.IllegalValueException;
import seedu.meeting.commons.util.ColorUtil;
import seedu.meeting.model.group.Group;
import seedu.meeting.model.meeting.Meeting;
import seedu.meeting.model.person.UniquePersonList;
import seedu.meeting.model.shared.Description;
import seedu.meeting.model.shared.Title;


// @@author Derek-Hardy
/**
 * JAXB-friendly version of the Group.
 */
public class XmlAdaptedGroup {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Group's %s field is missing!";

    @XmlElement
    private String color;
    @XmlElement(required = true)
    private String title;
    @XmlElement(required = true)
    private String description;
    @XmlElement(required = true)
    private XmlAdaptedMeeting meeting;

    @XmlElement
    private List<XmlAdaptedPerson> members = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedGroup.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedGroup() {}

    /**
     * Constructs an {@code XmlAdaptedGroup} with the given group details.
     */
    public XmlAdaptedGroup(String title, String description, XmlAdaptedMeeting meeting,
                           List<XmlAdaptedPerson> members, String color) {
        this.title = title;
        this.description = description;
        this.meeting = meeting;
        this.members.addAll(members);
        this.color = color;
    }

    /**
     * Converts a given Group into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedGroup
     */
    public XmlAdaptedGroup(Group source) {
        title = source.getTitle().fullTitle;
        if (source.getDescription() != null) {
            description = source.getDescription().statement;
        }
        if (source.getMeeting() != null) {
            meeting = new XmlAdaptedMeeting(source.getMeeting());
        }
        members = source.getMembersView().stream()
                .map(XmlAdaptedPerson::new)
                .collect(Collectors.toList());
        color = source.getColorString();
    }

    /**
     * Converts this jaxb-friendly adapted group object into the model's Group object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted group
     */
    public Group toModelType() throws IllegalValueException {
        final UniquePersonList modelMembers = new UniquePersonList();
        for (XmlAdaptedPerson person : members) {
            modelMembers.add(person.toModelType());
        }

        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Title.class.getSimpleName()));
        }
        if (!Title.isValidTitle(title)) {
            throw new IllegalValueException(Title.MESSAGE_TITLE_CONSTRAINTS);
        }
        final Title modelTitle = new Title(title);

        Optional<Description> modelDescription;
        if (description == null) {
            modelDescription = Optional.empty();
        } else {
            if (!Description.isValidDescription(description)) {
                throw new IllegalValueException(Description.MESSAGE_DESCRIPTION_CONSTRAINTS);
            }
            modelDescription = Optional.of(new Description(description));
        }

        Optional<Meeting> modelMeeting;
        if (meeting == null) {
            modelMeeting = Optional.empty();
        } else {
            modelMeeting = Optional.of(meeting.toModelType());
        }

        String modelColor;
        if (color == null) {
            modelColor = ColorUtil.getRandomColorString();
        } else {
            modelColor = color;
        }

        return new Group(modelTitle, modelDescription,
                modelMeeting, modelMembers, modelColor);
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedGroup)) {
            return false;
        }

        XmlAdaptedGroup otherGroup = (XmlAdaptedGroup) other;
        return Objects.equals(title, otherGroup.title)
                && Objects.equals(description, otherGroup.description)
                && Objects.equals(meeting, otherGroup.meeting)
                && Objects.equals(members, otherGroup.members)
                && Objects.equals(color, otherGroup.color);
    }
}
