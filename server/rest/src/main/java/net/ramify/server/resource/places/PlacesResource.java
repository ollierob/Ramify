package net.ramify.server.resource.places;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.collection.Places;
import net.ramify.model.place.position.Position;
import net.ramify.server.resource.RootResource;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import static net.ramify.server.resource.Resource.APPLICATION_PROTOBUF;

@Path("places")
@Produces({MediaType.TEXT_PLAIN, APPLICATION_PROTOBUF})
public interface PlacesResource extends RootResource {

    @GET
    @Path("at/{id}")
    Place at(@PathParam("id") PlaceId id);

    @GET
    @Path("children/{id}")
    Places within(
            @PathParam("id") PlaceId id,
            @QueryParam("depth") @DefaultValue("1") int depth);

    @GET
    @Path("position/{id}")
    Position position(@PathParam("id") PlaceId id);

    @Path("churches")
    ChurchesResource churches();

}
