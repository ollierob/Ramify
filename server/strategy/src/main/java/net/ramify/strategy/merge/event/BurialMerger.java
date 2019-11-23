package net.ramify.strategy.merge.event;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.type.burial.Burial;
import net.ramify.model.person.PersonId;
import net.ramify.model.place.Place;
import net.ramify.strategy.merge.place.PlaceMerger;

public class BurialMerger extends AbstractEventMerger<Burial> {

    BurialMerger(final PlaceMerger placeMerger) {
        super(placeMerger);
    }

    @Override
    Burial merge(final PersonId id, final DateRange date, final Place place) {
        throw new UnsupportedOperationException(); //TODO
    }

}
