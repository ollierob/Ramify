package net.ramify.server.resource.events;

import com.google.common.collect.Sets;
import net.ramify.model.date.ClosedDateRange;
import net.ramify.model.event.historic.HistoricEvent;
import net.ramify.model.event.historic.HistoricEventProvider;
import net.ramify.model.event.historic.HistoricEvents;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.provider.PlaceProvider;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.LocalDate;
import java.util.Set;

@Singleton
public class DefaultHistoricEventsResource implements HistoricEventsResource {

    private final HistoricEventProvider eventProvider;
    private final PlaceProvider placeProvider;

    @Inject
    DefaultHistoricEventsResource(final HistoricEventProvider eventProvider, final PlaceProvider placeProvider) {
        this.eventProvider = eventProvider;
        this.placeProvider = placeProvider;
    }

    @Override
    public HistoricEvents events(final LocalDate start, final LocalDate end, final Set<PlaceId> placeIds) {
        final var date = ClosedDateRange.of(start, end);
        final var events = Sets.<HistoricEvent>newHashSet();
        for (final var placeId : placeIds) {
            final var place = placeProvider.require(placeId);
            events.addAll(eventProvider.within(place, date));
        }
        events.addAll(eventProvider.global(date));
        return HistoricEvents.of(events, start);
    }

}
