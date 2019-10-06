package net.ramify.model.family.xml;

import net.ramify.model.ParserContext;
import net.ramify.model.event.Event;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;
import net.ramify.model.record.GenericRecordPerson;
import net.ramify.model.record.xml.record.XmlPersonRecord;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collections;
import java.util.Set;

@XmlRootElement(namespace = XmlFamily.NAMESPACE, name = "person")
public class XmlFamilyPerson extends XmlPersonRecord {

    @XmlAttribute(name = "id", required = true)
    private String id;

    @XmlAttribute(name = "father")
    private String father;

    @XmlAttribute(name = "mother")
    private String mother;

    @Override
    protected PersonId personId() {
        return new PersonId(id);
    }

    protected Person toPerson(final ParserContext context) {
        return new GenericRecordPerson(
                this.personId(),
                this.name(context.nameParser()),
                this.gender(),
                this.events(),
                this.notes());
    }

    protected Set<Event> events() {
        return Collections.emptySet();
    }

}
