package net.ramify.strategy.merge.event;

import net.ramify.model.event.EventBuilder;
import net.ramify.model.event.type.burial.BurialEvent;
import net.ramify.model.person.PersonId;
import net.ramify.model.place.merge.PlaceMerger;

public class BurialEventMerger extends AbstractEventMerger<BurialEvent> {

    BurialEventMerger(final PlaceMerger placeMerger) {
        super(placeMerger);
    }

    @Override
    BurialEvent merge(PersonId id, EventBuilder builder) {
        return builder.toBurial(id);
    }

}
