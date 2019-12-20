package net.ramify.server.resource.events;

import net.ramify.model.event.historic.HistoricEvents;
import net.ramify.model.place.PlaceId;
import net.ramify.server.resource.Resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.util.Set;

import static net.ramify.server.resource.Resource.APPLICATION_PROTOBUF;

@Produces({MediaType.TEXT_PLAIN, APPLICATION_PROTOBUF})
public interface HistoricEventsResource extends Resource {

    @SuppressWarnings("RestParamTypeInspection")
    @GET
    @Path("{start}/{end}")
    HistoricEvents events(
            @PathParam("start") LocalDate start,
            @PathParam("end") LocalDate end,
            @QueryParam("place") Set<PlaceId> placeIds);

}
