package net.ramify.strategy.merge.event;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.type.BirthEvent;
import net.ramify.model.event.type.birth.GenericBirth;
import net.ramify.model.person.PersonId;
import net.ramify.model.place.Place;
import net.ramify.strategy.merge.place.PlaceMerger;

public class BirthMerger extends AbstractEventMerger<BirthEvent> {

    public BirthMerger(final PlaceMerger placeMerger) {
        super(placeMerger);
    }

    @Override
    BirthEvent merge(final PersonId id, final DateRange date, final Place place) {
        return new GenericBirth(id, date).with(place);
    }

}
