package net.ramify.strategy.merge.event;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.type.DeathEvent;
import net.ramify.model.person.PersonId;
import net.ramify.model.place.Place;
import net.ramify.strategy.merge.place.PlaceMerger;

public class DeathMerger extends AbstractEventMerger<DeathEvent> {

    public DeathMerger(final PlaceMerger placeMerger) {
        super(placeMerger);
    }

    @Override
    DeathEvent merge(final PersonId id, final DateRange date, final Place place) {
        throw new UnsupportedOperationException();
    }

}
