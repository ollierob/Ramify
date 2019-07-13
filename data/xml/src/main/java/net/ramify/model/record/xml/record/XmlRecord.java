package net.ramify.model.record.xml.record;

import net.ramify.model.person.name.Name;
import net.ramify.model.person.name.NameParser;
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

    @Nonnull
    protected Name name(final NameParser parser) {
        if (name != null) return parser.parse(name);
        throw new UnsupportedOperationException(); //TODO add extra naming elements
    }

    protected RecordId recordId() {
        return new RecordId(UUID.randomUUID().toString()); //FIXME
    }

}
