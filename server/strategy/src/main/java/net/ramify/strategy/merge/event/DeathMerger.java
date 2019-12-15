package net.ramify.strategy.merge.event;

import net.ramify.model.event.EventBuilder;
import net.ramify.model.event.type.DeathEvent;
import net.ramify.model.person.PersonId;
import net.ramify.model.place.merge.PlaceMerger;

public class DeathMerger extends AbstractEventMerger<DeathEvent> {

    public DeathMerger(final PlaceMerger placeMerger) {
        super(placeMerger);
    }

    @Override
    DeathEvent merge(PersonId id, EventBuilder builder) {
        return builder.toDeath(id);
    }

}
