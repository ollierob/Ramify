package net.ramify.server.resource.events;

import net.ramify.model.date.ClosedDateRange;
import net.ramify.model.event.historic.HistoricEventProvider;
import net.ramify.model.event.historic.HistoricEvents;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.provider.PlaceProvider;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.LocalDate;

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
    public HistoricEvents events(final LocalDate start, final LocalDate end, final PlaceId placeId) {
        return HistoricEvents.of(eventProvider.within(placeProvider.require(placeId), ClosedDateRange.of(start, end)));
    }
    
}
