package net.ramify.model.event.type.marriage;

import com.google.common.collect.ImmutableSet;
import net.ramify.model.event.EventId;
import net.ramify.model.event.EventProperties;
import net.ramify.model.event.EventWithProperties;
import net.ramify.model.event.type.LifeEvent;
import net.ramify.model.person.PersonId;
import net.ramify.model.place.Place;

import javax.annotation.Nonnull;
import java.util.Set;

public class GenericMarriage implements EventWithProperties, LifeEvent, MarriageEvent {

    private final EventId eventId;
    private final PersonId firstSpouse;
    private final PersonId secondSpouse;
    private final EventProperties properties;

    public GenericMarriage(
            final EventId eventId,
            final PersonId firstSpouse,
            final PersonId secondSpouse,
            final EventProperties properties) {
        this.eventId = eventId;
        this.firstSpouse = firstSpouse;
        this.secondSpouse = secondSpouse;
        this.properties = properties;
    }

    @Nonnull
    @Override
    public EventProperties properties() {
        return properties;
    }

    @Nonnull
    @Override
    public EventId eventId() {
        return eventId;
    }

    @Nonnull
    @Override
    public Set<PersonId> personIds() {
        return ImmutableSet.of(firstSpouse, secondSpouse);
    }

    public MarriageEvent with(final Place place) {
        return place == null ? this : new GenericMarriageWithPlace(eventId, firstSpouse, secondSpouse, properties, place);
    }

    @Nonnull
    @Override
    public String title() {
        return "Marriage";
    }

}
