package net.ramify.model.event.xml;

import net.ramify.model.event.Event;
import net.ramify.model.event.type.Birth;
import net.ramify.model.event.type.Death;
import net.ramify.model.event.type.birth.GenericBirth;
import net.ramify.model.event.type.death.GenericDeath;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.age.Age;
import net.ramify.model.person.xml.XmlAge;
import net.ramify.model.record.xml.RecordContext;

import javax.annotation.CheckForNull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collections;
import java.util.Set;

@XmlRootElement(namespace = XmlEvent.NAMESPACE, name = "death")
public class XmlDeathEvent extends XmlEvent {

    @XmlAttribute(name = "age")
    private Integer simpleAge;

    @XmlElementRef(required = false)
    private XmlAge complexAge;

    @Override
    public Death toEvent(final PersonId personId, final RecordContext context) {
        return new GenericDeath(personId, this.date(context)).with(this.age());
    }

    @Override
    public Set<Event> inferredEvents(final PersonId personId, final RecordContext context) {
        final var birth = this.birth(personId, context);
        return birth == null ? Collections.emptySet() : Collections.singleton(birth);
    }

    @CheckForNull
    Age age() {
        if (simpleAge != null) return Age.ofYears(simpleAge);
        if (complexAge != null) return complexAge.age();
        return null;
    }

    Birth birth(final PersonId personId, final RecordContext context) {
        final var age = this.age();
        return age == null ? null : new GenericBirth(personId, age.birthDate(this.date(context)));
    }

}
