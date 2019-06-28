package net.ramify.server.resource.places;

import net.ramify.model.place.PlaceId;
import net.ramify.server.resource.RootResource;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("places")
public interface PlacesResource extends RootResource {

    @Path("{region}/churches")
    ChurchesResource churches(@PathParam("region") PlaceId regionId);

}
