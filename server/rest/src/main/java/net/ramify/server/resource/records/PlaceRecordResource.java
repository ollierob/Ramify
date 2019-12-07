package net.ramify.server.resource.records;

import net.ramify.model.place.PlaceId;
import net.ramify.model.record.collection.IndividualRecords;
import net.ramify.model.record.collection.Records;
import net.ramify.server.resource.Resource;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import static net.ramify.server.resource.Resource.APPLICATION_PROTOBUF;

@Produces({MediaType.TEXT_PLAIN, APPLICATION_PROTOBUF})
public interface PlaceRecordResource extends Resource {

    @GET
    @Path("families/{id}")
    Records at(
            @PathParam("id") PlaceId placeId,
            @QueryParam("start") int start,
            @QueryParam("limit") @DefaultValue("20") int limit);

    @GET
    @Path("individuals/{id}")
    IndividualRecords individuals(@PathParam("id") PlaceId placeId);

}
