package net.ramify.model.event.type.marriage;

import net.ramify.model.event.EventId;
import net.ramify.model.event.EventProperties;
import net.ramify.model.person.PersonId;
import net.ramify.model.place.HasPlace;
import net.ramify.model.place.Place;

import javax.annotation.Nonnull;

class GenericMarriageWithPlace extends GenericMarriage implements HasPlace {

    private final Place place;

    GenericMarriageWithPlace(
            final EventId id,
            final PersonId firstSpouse,
            final PersonId secondSpouse,
            final EventProperties properties,
            @Nonnull final Place place) {
        super(id, firstSpouse, secondSpouse, properties);
        this.place = place;
    }

    @Nonnull
    @Override
    public Place place() {
        return place;
    }

}
