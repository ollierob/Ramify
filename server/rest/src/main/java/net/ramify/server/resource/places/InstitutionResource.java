package net.ramify.server.resource.places;

import net.ramify.model.place.PlaceId;
import net.ramify.model.place.collection.PlaceIds;
import net.ramify.model.place.institution.InstitutionInfo;
import net.ramify.server.resource.Resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import static net.ramify.server.resource.Resource.APPLICATION_PROTOBUF;

@Produces({MediaType.TEXT_PLAIN, APPLICATION_PROTOBUF})
public interface InstitutionResource extends Resource {

    @GET
    @Path("at/{id}")
    InstitutionInfo info(@PathParam("id") PlaceId churchId);

    @GET
    @Path("in/{region}")
    PlaceIds inRegion(@PathParam("region") PlaceId region);

}
