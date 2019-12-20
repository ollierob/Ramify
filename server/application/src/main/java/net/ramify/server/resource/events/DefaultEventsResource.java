package net.ramify.server.resource.events;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DefaultEventsResource implements EventsResource {

    private final HistoricEventsResource historic;

    @Inject
    DefaultEventsResource(final HistoricEventsResource historic) {
        this.historic = historic;
    }

    @Override
    public HistoricEventsResource historicEvents() {
        return historic;
    }

}
