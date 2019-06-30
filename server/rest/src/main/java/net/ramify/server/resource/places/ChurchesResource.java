package net.ramify.server.resource.places;

import net.ramify.model.place.PlaceId;
import net.ramify.model.place.church.ChurchInfo;
import net.ramify.server.resource.Resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.Collection;

public interface ChurchesResource extends Resource {

    @GET
    @Path("at/{id}")
    ChurchInfo info(@PathParam("id") PlaceId churchId);

    @GET
    @Path("in/{region}")
    Collection<PlaceId> inRegion(@PathParam("region") PlaceId region);

}
