package net.ramify.server.resource.events;

import net.ramify.server.resource.RootResource;

import javax.ws.rs.Path;

@Path("events")
public interface EventsResource extends RootResource {

    @Path("historic")
    HistoricEventsResource historicEvents();

}
