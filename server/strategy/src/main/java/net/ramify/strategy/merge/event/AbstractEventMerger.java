package net.ramify.strategy.merge.event;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.EventBuilder;
import net.ramify.model.event.merge.EventMerger;
import net.ramify.model.event.type.UniqueEvent;
import net.ramify.model.person.PersonId;
import net.ramify.model.place.HasPlace;
import net.ramify.model.place.Place;
import net.ramify.model.place.merge.PlaceMerger;

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
        if (date.isEmpty()) return this.impossible(e1, e2);

        final var place = placeMerger.merge(HasPlace.getPlace(e1), HasPlace.getPlace(e2));
        if (place.isImpossibleMerge()) return this.impossible(e1, e2);

        final var builder = this.eventBuilder(date.get(), place.orElse(null))
                .setInferred(e1.isInferred() && e2.isInferred());

        return Result.of(this.merge(e1.personId(), builder));

    }

    Result<E> impossible(final E e1, final E e2) {
        return Result.impossible();
    }

    EventBuilder eventBuilder(final DateRange date, final Place place) {
        return EventBuilder.builderWithRandomId(date).withPlace(place);
    }

    abstract E merge(PersonId id, EventBuilder builder);

}
