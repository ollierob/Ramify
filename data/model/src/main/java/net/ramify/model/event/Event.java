package net.ramify.model.event;

import net.ramify.model.Castable;
import net.ramify.model.place.Place;

import javax.annotation.Nonnull;
import java.util.Comparator;

public interface Event extends Castable<Event> {

    @Nonnull
    Comparator<Event> COMPARE_BY_DATE = (e1, e2) -> DateRange.COMPARE_BY_EARLIEST.compare(e1.date(), e2.date());

    @Nonnull
    DateRange date();

    @Nonnull
    String description();

    @Nonnull
    Place place();

}
