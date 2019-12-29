package net.ramify.server.resource.records;

import net.ramify.model.record.RecordId;
import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.collection.Records;
import net.ramify.model.record.image.ImageId;
import net.ramify.model.record.image.RecordImages;
import net.ramify.server.resource.RootResource;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static net.ramify.server.resource.Resource.APPLICATION_PROTOBUF;

@Path("records")
@Produces({MediaType.TEXT_PLAIN, APPLICATION_PROTOBUF})
public interface RecordsResource extends RootResource {

    @GET
    @Path("in/{id}")
    Records records(
            @PathParam("id") RecordSetId id,
            @QueryParam("children") boolean includeChildren,
            @QueryParam("start") int start,
            @QueryParam("limit") @DefaultValue("50") int limit);

    @Path("sets")
    RecordSetResource recordSets();

    @Path("individuals")
    IndividualRecordResource individuals();

    @GET
    @Path("images/set/{id}")
    RecordImages images(@PathParam("id") RecordSetId id);

    @GET
    @Path("images/record/{recordId}/{imageId}")
    @Produces("image/*")
    Response image(
            @PathParam("recordId") RecordId recordId,
            @PathParam("imageId") ImageId imageId);

    @Path("places")
    PlaceRecordResource places();

}
