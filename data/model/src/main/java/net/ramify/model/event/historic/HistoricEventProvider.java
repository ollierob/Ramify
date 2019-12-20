package net.ramify.model.event.historic;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.EventId;
import net.ramify.model.place.Place;
import net.ramify.model.util.provider.Provider;

import javax.annotation.Nonnull;
import java.util.Set;

public interface HistoricEventProvider extends Provider<EventId, HistoricEvent> {

    @Nonnull
    Set<HistoricEvent> within(Place place, DateRange date);

}
