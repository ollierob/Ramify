package net.ramify.model.event;

import com.google.common.collect.Sets;
import net.ramify.model.date.DateRange;
import net.ramify.model.event.historic.HistoricEvent;
import net.ramify.model.event.type.BirthEvent;
import net.ramify.model.event.type.DeathEvent;
import net.ramify.model.event.type.LifeEvent;
import net.ramify.model.event.type.birth.BaptismEvent;
import net.ramify.model.event.type.birth.GenericBaptismEvent;
import net.ramify.model.event.type.birth.GenericBirthEvent;
import net.ramify.model.event.type.burial.BurialEvent;
import net.ramify.model.event.type.burial.GenericBurialEvent;
import net.ramify.model.event.type.death.GenericDeathEvent;
import net.ramify.model.event.type.historic.GenericHistoricEvent;
import net.ramify.model.event.type.marriage.GenericMarriageEvent;
import net.ramify.model.event.type.marriage.MarriageEvent;
import net.ramify.model.event.type.misc.Flourished;
import net.ramify.model.event.type.misc.GenericLifeEvent;
import net.ramify.model.event.type.residence.GenericResidenceEvent;
import net.ramify.model.event.type.residence.ResidenceEvent;
import net.ramify.model.event.type.will.GenericProbateEvent;
import net.ramify.model.event.type.will.ProbateEvent;
import net.ramify.model.person.HasPersonId;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.age.Age;
import net.ramify.model.place.Place;
import net.ramify.model.place.type.SettlementOrRegion;
import net.ramify.model.util.Link;

import java.util.Set;

public class EventBuilder {

    private final EventId eventId;
    private final DateRange date;
    private Place place;
    private Age givenAge;
    private String occupation;
    private String description;
    private boolean inferred;
    private Set<Link> links;

    private EventBuilder(final EventId eventId, final DateRange date) {
        this.eventId = eventId;
        this.date = date;
    }

    public static EventBuilder builder(final EventId id, final DateRange date) {
        return new EventBuilder(id, date);
    }

    public static EventBuilder builderWithRandomId(final DateRange date) {
        return builder(EventId.random(), date);
    }

    public EventBuilder withGivenAge(final Age age) {
        this.givenAge = age;
        return this;
    }

    public EventBuilder withPlace(final Place place) {
        this.place = place;
        return this;
    }

    public EventBuilder withOccupation(final String occupation) {
        this.occupation = occupation;
        return this;
    }

    public EventBuilder withLink(final Link link) {
        if (link == null) return this;
        if (links == null) links = Sets.newHashSetWithExpectedSize(2);
        links.add(link);
        return this;
    }

    public EventBuilder withDescription(final String description) {
        this.description = description;
        return this;
    }

    public EventBuilder setInferred(final boolean inferred) {
        this.inferred = inferred;
        return this;
    }

    protected EventProperties eventProperties() {
        return new EventProperties(date, givenAge, occupation, inferred, description);
    }

    public BirthEvent toBirth(final HasPersonId person) {
        return new GenericBirthEvent(eventId, person.personId(), this.eventProperties()).with(place);
    }

    public MarriageEvent toMarriage(final HasPersonId person) {
        return new GenericMarriageEvent(eventId, person.personId(), this.eventProperties()).with(place);
    }

    public ResidenceEvent toResidence(final HasPersonId person) {
        return new GenericResidenceEvent(eventId, person.personId(), this.eventProperties(), place);
    }

    public LifeEvent toFlourished(final PersonId personId) {
        return new Flourished(eventId, personId, this.eventProperties());
    }

    public DeathEvent toDeath(final HasPersonId person) {
        return this.toDeath(person, null);
    }

    public DeathEvent toDeath(final HasPersonId person, final String cause) {
        return new GenericDeathEvent(eventId, person.personId(), this.eventProperties(), cause).with(place);
    }

    public BurialEvent toBurial(final PersonId personId) {
        return new GenericBurialEvent(eventId, personId, this.eventProperties(), place);
    }

    public BaptismEvent toBaptism(final HasPersonId person) {
        return new GenericBaptismEvent(eventId, person.personId(), this.eventProperties()).with(place);
    }

    public ProbateEvent toProbate(final HasPersonId person) {
        return new GenericProbateEvent(eventId, person.personId(), this.eventProperties());
    }

    public HistoricEvent toHistoric(final String title, final SettlementOrRegion region) {
        return new GenericHistoricEvent(eventId, date, place, region, title, description, links);
    }

    public LifeEvent toAnyLifeEvent(final HasPersonId person, final String title) {
        return new GenericLifeEvent(title, eventId, person.personId(), this.eventProperties());
    }

}
