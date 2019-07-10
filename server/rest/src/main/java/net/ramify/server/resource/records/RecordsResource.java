package net.ramify.server.resource.records;

import net.ramify.model.date.DateRange;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.server.resource.RootResource;

import javax.annotation.Nonnull;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.Set;

@Path("records")
public interface RecordsResource extends RootResource {

    @Nonnull
    @Path("sets")
    Set<RecordSet> recordSets(
            @QueryParam("place") PlaceId withinPlace,
            @QueryParam("date") DateRange withinDate,
            @QueryParam("limit") @DefaultValue("20") int limit);

}
