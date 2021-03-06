package net.ramify.server.resource.records;

import net.ramify.model.record.collection.IndividualRecords;
import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.proto.RecordProto;
import net.ramify.server.resource.Resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import static net.ramify.server.resource.Resource.APPLICATION_PROTOBUF;

@Produces({MediaType.TEXT_PLAIN, APPLICATION_PROTOBUF})
public interface IndividualRecordResource extends Resource {

    @GET
    @Path("in/{id}")
    IndividualRecords in(
            @PathParam("id") RecordSetId id,
            @QueryParam("children") boolean includeChildren,
            @QueryParam("start") int start,
            @QueryParam("limit") @DefaultValue("50") int limit);

    @POST
    @Consumes({APPLICATION_PROTOBUF})
    @Path("search")
    IndividualRecords search(RecordProto.RecordSearch searchParameters);

}
