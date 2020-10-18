package net.ramify.server.resource.places;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroup;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.collection.Places;
import net.ramify.model.place.position.Position;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.server.resource.RootResource;

import javax.annotation.CheckForNull;
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
    @Path("group/find/{id}")
    PlaceGroup group(@PathParam("id") PlaceId id);

    @GET
    @Path("group/at/{id}")
    @CheckForNull
    PlaceGroup group(@PathParam("id") PlaceGroupId id);

    @GET
    @Path("at/{id}")
    @CheckForNull
    Place at(@PathParam("id") PlaceId id);

    @GET
    @Path("children/{id}")
    Places within(
            @PathParam("id") PlaceId id,
            @QueryParam("depth") @DefaultValue("2") Integer depth);

    @GET
    @Path("countries")
    Places countries(@QueryParam("onlyTopLevel") boolean onlyTopLevel);

    @GET
    @Path("position/{id}")
    @CheckForNull
    Position position(@PathParam("id") PlaceId id);

    @GET
    @Path("describe/{id}")
    @CheckForNull
    @Produces(MediaType.TEXT_HTML)
    String describe(@PathParam("id") PlaceId id);

    @GET
    @Path("describe/{id}/type")
    @CheckForNull
    @Produces(MediaType.TEXT_HTML)
    String describeType(@PathParam("id") PlaceId id);

    @GET
    @CheckForNull
    @Path("bundle/{id}")
    PlaceProto.PlaceBundle bundle(@PathParam("id") PlaceId id);

    @Path("favourites")
    PlaceFavouritesResource favourites();

    @GET
    @Path("search")
    Places find(
            @QueryParam("name") String name,
            @QueryParam("within") PlaceId within,
            @QueryParam("limit") @DefaultValue("100") int limit);

}
