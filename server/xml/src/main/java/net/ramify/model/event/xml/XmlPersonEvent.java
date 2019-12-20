package net.ramify.model.event.xml;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.PersonEvent;
import net.ramify.model.event.EventBuilder;
import net.ramify.model.event.EventId;
import net.ramify.model.event.type.BirthEvent;
import net.ramify.model.person.HasPersonId;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.age.Age;
import net.ramify.model.person.xml.XmlAge;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.relationship.Relationship;
import net.ramify.utils.collections.SetUtils;
import net.ramify.utils.objects.Functions;

import javax.annotation.CheckForNull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.Collections;
import java.util.Set;

@XmlSeeAlso({XmlBirthEvent.class, XmlLifeEvent.class, XmlDeathEvent.class, XmlPostDeathEvent.class})
@XmlRootElement(namespace = XmlEvent.NAMESPACE, name = "personEvent")
public abstract class XmlPersonEvent extends XmlEvent {

    @XmlAttribute(name = "placeId")
    private String placeId;

    @XmlAttribute(name = "age")
    private Integer simpleAge;

    @XmlElementRef(required = false)
    private XmlAge complexAge;

    @XmlAttribute(name = "occupation")
    private String occupation;

    @CheckForNull
    protected PlaceId placeId() {
        return Functions.ifNonNull(placeId, PlaceId::new);
    }

    @CheckForNull
    protected Place place(final RecordContext context) {
        final var placeId = this.placeId();
        return Functions.ifNonNull(placeId, context.places()::require);
    }

    public abstract PersonEvent toEvent(PersonId personId, RecordContext context);

    public Set<PersonEvent> allEvents(final PersonId personId, final RecordContext context, final boolean inferredEvents) {
        final var event = this.toEvent(personId, context);
        return inferredEvents
                ? SetUtils.with(this.inferredEvents(personId, context), event)
                : Collections.singleton(event);
    }

    protected Set<PersonEvent> inferredEvents(PersonId personId, RecordContext context) {
        final var birth = this.inferBirth(personId, context);
        return birth == null ? Collections.emptySet() : Collections.singleton(birth);
    }

    protected BirthEvent inferBirth(final PersonId personId, final RecordContext context) {
        final var age = this.age();
        if (age == null) return null;
        return this.eventBuilder(this.randomEventId(), age.birthDate(this.date(context)))
                .withGivenAge(age)
                .setInferred(true)
                .toBirth(personId);
    }

    public Set<Relationship> inferredRelationships(final HasPersonId personId) {
        return Collections.emptySet();
    }

    @CheckForNull
    protected Age age() {
        if (simpleAge != null) return Age.ofYears(simpleAge);
        if (complexAge != null) return complexAge.age();
        return null;
    }

    protected EventId randomEventId() {
        return EventId.random();
    }

    protected String occupation() {
        return occupation;
    }

    protected EventBuilder eventBuilder(final RecordContext context) {
        return this.eventBuilder(this.eventId(), this.date(context))
                .withPlace(this.place(context))
                .withOccupation(this.occupation());
    }

    protected EventBuilder eventBuilder(final EventId eventId, final DateRange date) {
        return EventBuilder.builder(eventId, date)
                .withGivenAge(this.age());
    }

}
