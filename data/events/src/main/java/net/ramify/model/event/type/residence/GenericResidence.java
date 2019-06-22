package net.ramify.model.event.type.residence;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.AbstractEvent;
import net.ramify.model.event.type.LifeEvent;
import net.ramify.model.person.PersonId;
import net.ramify.model.place.HasPlaceId;
import net.ramify.model.place.PlaceId;

import javax.annotation.Nonnull;

public class GenericResidence extends AbstractEvent<GenericResidence> implements LifeEvent, HasPlaceId {

    private final PlaceId placeId;

    public GenericResidence(final PersonId personId, final DateRange date, final PlaceId placeId) {
        super(personId, date);
        this.placeId = placeId;
    }

    @Nonnull
    @Override
    public PlaceId placeId() {
        return placeId;
    }

    @Nonnull
    @Override
    public String title() {
        return "Residence";
    }

}
