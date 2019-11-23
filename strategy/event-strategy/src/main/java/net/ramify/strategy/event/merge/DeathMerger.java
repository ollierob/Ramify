package net.ramify.strategy.event.merge;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.type.Death;
import net.ramify.model.event.type.death.GenericDeath;
import net.ramify.model.person.PersonId;
import net.ramify.strategy.place.merge.PlaceMerger;

public class DeathMerger extends AbstractEventMerger<Death> {

    public DeathMerger(final PlaceMerger placeMerger) {
        super(placeMerger);
    }

    @Override
    Death merge(final PersonId id, final DateRange date) {
        return new GenericDeath(id, date);
    }

}
