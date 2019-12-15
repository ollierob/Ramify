package net.ramify.model.record.xml.record;

import com.google.common.base.MoreObjects;
import net.ramify.model.event.collection.MutablePersonEventSet;
import net.ramify.model.event.xml.XmlEvent;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.gender.Gender;
import net.ramify.model.person.gender.Sex;
import net.ramify.model.person.name.Name;
import net.ramify.model.person.name.NameParser;
import net.ramify.model.person.xml.XmlGender;
import net.ramify.model.person.xml.XmlName;
import net.ramify.model.record.xml.RecordContext;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class XmlPersonRecord extends XmlRecord {

    @XmlAttribute(name = "id")
    private String id = UUID.randomUUID().toString();

    @XmlAttribute(name = "name")
    private String name;

    @XmlElementRef(required = false)
    private XmlName complexName;

    @XmlAttribute(name = "gender", required = true)
    private XmlGender gender;

    @XmlAttribute(name = "notes")
    private String notes;

    @XmlAttribute(name = "occupation")
    private String occupation;

    @XmlElementRef
    private List<XmlEvent> events;

    public PersonId personId() {
        return new PersonId(id);
    }

    @Nonnull
    protected Name name(final NameParser parser) {
        if (complexName != null) return complexName.build(parser);
        if (name != null) return parser.parse(name);
        throw new UnsupportedOperationException(); //TODO add extra naming elements
    }

    protected Gender gender() {
        return gender.gender();
    }

    protected Sex sex() {
        return gender.sex();
    }

    @CheckForNull
    protected String notes() {
        return notes;
    }

    @CheckForNull
    protected String occupation() {
        return occupation;
    }

    protected List<XmlEvent> events() {
        return MoreObjects.firstNonNull(events, Collections.emptyList());
    }

    protected MutablePersonEventSet events(final PersonId personId, final RecordContext context) {
        final var events = new MutablePersonEventSet(context.uniqueEventMerger());
        this.events().forEach(event -> events.addAll(event.allEvents(personId, context, false)));
        return events;
    }

}
