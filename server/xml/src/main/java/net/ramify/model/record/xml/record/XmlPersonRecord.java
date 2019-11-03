package net.ramify.model.record.xml.record;

import net.ramify.model.person.PersonId;
import net.ramify.model.person.gender.Gender;
import net.ramify.model.person.gender.Sex;
import net.ramify.model.person.name.Name;
import net.ramify.model.person.name.NameParser;
import net.ramify.model.person.xml.XmlGender;
import net.ramify.model.person.xml.XmlName;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import java.util.UUID;

public class XmlPersonRecord extends XmlRecord {

    @XmlAttribute(name = "name")
    private String name;

    @XmlElementRef(required = false)
    private XmlName complexName;

    @XmlAttribute(name = "gender", required = true)
    private XmlGender gender;

    @XmlAttribute(name = "notes")
    private String notes;

    @XmlAttribute(name = "id", required = false)
    private String id = UUID.randomUUID().toString();

    protected PersonId personId() {
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

    protected String notes() {
        return notes;
    }

}
