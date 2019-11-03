package net.ramify.server.resource.records;

import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.image.RecordImages;
import net.ramify.server.resource.RootResource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import static net.ramify.server.resource.Resource.APPLICATION_PROTOBUF;

@Path("records")
@Produces({MediaType.TEXT_PLAIN, APPLICATION_PROTOBUF})
public interface RecordsResource extends RootResource {

    @Path("sets")
    RecordSetResource recordSets();

    @Path("individuals")
    IndividualRecordResource individuals();

    @GET
    @Path("images/{id}")
    RecordImages images(@PathParam("id") RecordSetId id);

}
