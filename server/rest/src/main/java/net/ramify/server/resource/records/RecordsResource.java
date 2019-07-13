package net.ramify.server.resource.records;

import net.ramify.model.record.collection.Records;
import net.ramify.model.record.proto.RecordProto;
import net.ramify.server.resource.RootResource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import static net.ramify.server.resource.Resource.APPLICATION_PROTOBUF;

@Path("records")
@Produces({MediaType.TEXT_PLAIN, APPLICATION_PROTOBUF})
public interface RecordsResource extends RootResource {

    @Path("sets")
    RecordSetResource recordSets();

    @POST
    @Consumes({APPLICATION_PROTOBUF})
    @Path("search")
    Records search(RecordProto.RecordSearch searchParameters);

}
