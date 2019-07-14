package net.ramify.model.record.xml.record;

import net.ramify.model.person.PersonId;
import net.ramify.model.person.gender.Gender;
import net.ramify.model.person.name.Name;
import net.ramify.model.person.name.NameParser;
import net.ramify.model.person.xml.XmlGender;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.xml.record.residence.XmlResidenceRecord;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.UUID;

@XmlSeeAlso({XmlResidenceRecord.class})
public abstract class XmlRecord {

    public static final String NAMESPACE = "http://ramify.net/records";

    @XmlAttribute(name = "name", required = false)
    private String name;

    @XmlAttribute(name = "gender", required = true)
    private XmlGender gender;

    @XmlAttribute(name = "notes", required = false)
    private String notes;

    @Nonnull
    protected Name name(final NameParser parser) {
        if (name != null) return parser.parse(name);
        throw new UnsupportedOperationException(); //TODO add extra naming elements
    }

    protected RecordId recordId() {
        return new RecordId(UUID.randomUUID().toString()); //FIXME
    }

    protected PersonId personId() {
        return new PersonId(UUID.randomUUID().toString());
    }

    protected Gender gender() {
        return gender.gender();
    }

    protected String notes() {
        return notes;
    }

}
