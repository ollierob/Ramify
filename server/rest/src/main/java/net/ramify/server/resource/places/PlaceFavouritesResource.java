package net.ramify.server.resource.places;

import net.ramify.model.place.PlaceId;
import net.ramify.model.place.collection.PlaceIds;
import net.ramify.server.resource.Resource;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import static net.ramify.server.resource.Resource.APPLICATION_PROTOBUF;

@Produces({MediaType.TEXT_PLAIN, APPLICATION_PROTOBUF})
public interface PlaceFavouritesResource extends Resource {

    @GET
    @Path("all")
    PlaceIds all();

    @POST
    @Path("add")
    PlaceIds add(PlaceId placeId);

    @DELETE
    @Path("remove/{id}")
    PlaceIds remove(@PathParam("id") PlaceId placeId);

}
