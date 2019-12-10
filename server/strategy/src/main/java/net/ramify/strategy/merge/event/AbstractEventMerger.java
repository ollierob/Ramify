package net.ramify.strategy.merge.event;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.type.UniqueEvent;
import net.ramify.model.person.PersonId;
import net.ramify.model.place.HasPlace;
import net.ramify.model.place.Place;
import net.ramify.strategy.merge.place.PlaceMerger;

import javax.annotation.Nonnull;

abstract class AbstractEventMerger<E extends UniqueEvent> implements EventMerger<E> {

    private final PlaceMerger placeMerger;

    AbstractEventMerger(final PlaceMerger placeMerger) {
        this.placeMerger = placeMerger;
    }

    @Nonnull
    @Override
    public Result<E> merge(final E e1, final E e2) {

        final var date = e1.date().intersection(e2.date());
        if (date.isEmpty()) return Result.impossible();

        final var place = placeMerger.merge(HasPlace.place(e1), HasPlace.place(e2));
        if (place.isImpossibleMerge()) return Result.impossible();

        return Result.of(this.merge(e1.personId(), date.get(), place.orElse(null)));

    }

    abstract E merge(PersonId id, DateRange date, Place place);

}
