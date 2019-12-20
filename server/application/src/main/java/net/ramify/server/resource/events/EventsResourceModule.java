package net.ramify.server.resource.events;

import com.google.inject.AbstractModule;

public class EventsResourceModule extends AbstractModule {

    @Override
    protected void configure() {
        super.configure();
        this.bind(EventsResource.class).to(DefaultEventsResource.class);
        this.bind(HistoricEventsResource.class).to(DefaultHistoricEventsResource.class);
    }

}
