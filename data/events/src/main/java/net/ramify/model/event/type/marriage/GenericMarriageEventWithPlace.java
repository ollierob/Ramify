package net.ramify.model.event.type.marriage;

import net.ramify.model.event.EventId;
import net.ramify.model.event.EventProperties;
import net.ramify.model.person.PersonId;
import net.ramify.model.place.HasPlace;
import net.ramify.model.place.Place;

import javax.annotation.Nonnull;

class GenericMarriageEventWithPlace extends GenericMarriageEvent implements HasPlace {

    private final Place place;

    GenericMarriageEventWithPlace(
            final EventId id,
            final PersonId personId,
            final EventProperties properties,
            @Nonnull final Place place) {
        super(id, personId, properties);
        this.place = place;
    }

    @Nonnull
    @Override
    public Place place() {
        return place;
    }

}
