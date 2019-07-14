package net.ramify.server.resource.records;

import net.ramify.model.date.DateRange;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.collection.RecordSets;
import net.ramify.server.resource.Resource;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import static net.ramify.server.resource.Resource.APPLICATION_PROTOBUF;

@Produces({MediaType.TEXT_PLAIN, APPLICATION_PROTOBUF})
public interface RecordSetResource extends Resource {

    @Nonnull
    @GET
    RecordSets recordSets(
            @QueryParam("name") String name,
            @QueryParam("place") PlaceId withinPlace,
            @QueryParam("date") DateRange withinDate,
            @QueryParam("limit") @DefaultValue("20") int limit);

    @CheckForNull
    @GET
    @Path("{id}")
    RecordSet recordSet(@PathParam("id") RecordSetId id);

    @GET
    @Path("children/{id}")
    RecordSets children(@PathParam("id") RecordSetId parentId);

}
