package net.ramify.strategy.merge.event;

import net.ramify.model.event.EventBuilder;
import net.ramify.model.event.type.BirthEvent;
import net.ramify.model.person.PersonId;
import net.ramify.model.place.merge.PlaceMerger;

public class BirthEventMerger extends AbstractEventMerger<BirthEvent> {

    public BirthEventMerger(final PlaceMerger placeMerger) {
        super(placeMerger);
    }

    @Override
    BirthEvent merge(final PersonId person, final EventBuilder builder) {
        return builder.toBirth(person);
    }

}
