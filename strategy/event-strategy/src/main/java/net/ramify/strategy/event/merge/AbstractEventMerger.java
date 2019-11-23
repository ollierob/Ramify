package net.ramify.strategy.event.merge;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.Event;
import net.ramify.model.person.PersonId;
import net.ramify.strategy.merge.Merger;

import javax.annotation.Nonnull;

abstract class AbstractEventMerger<E extends Event> implements EventMerger<E> {

    @Nonnull
    @Override
    public Result<E> merge(final E e1, final E e2) {
        return e1.date().intersection(e2.date())
                .map(date -> Merger.value(this.merge(e1.personId(), date)))
                .orElseGet(Merger::impossible);
    }

    abstract E merge(PersonId id, DateRange date);

}
