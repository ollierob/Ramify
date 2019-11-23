package net.ramify.strategy.merge.event;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.Event;
import net.ramify.model.person.PersonId;
import net.ramify.strategy.merge.place.PlaceMerger;

import javax.annotation.Nonnull;

abstract class AbstractEventMerger<E extends Event> implements EventMerger<E> {

    private final PlaceMerger placeMerger;

    AbstractEventMerger(final PlaceMerger placeMerger) {
        this.placeMerger = placeMerger;
    }

    @Nonnull
    @Override
    public Result<E> merge(final E e1, final E e2) {
        //TODO check place merge
        return e1.date().intersection(e2.date())
                .map(date -> Result.of(this.merge(e1.personId(), date)))
                .orElseGet(Result::impossible);
    }

    abstract E merge(PersonId id, DateRange date);

}
