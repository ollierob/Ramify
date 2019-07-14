package net.ramify.server.resource.core;

import net.ramify.authentication.UserSessionContext;
import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.provider.RecordSetProvider;
import net.ramify.server.resource.AbstractResource;
import net.ramify.server.resource.Cached;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Singleton
@Path("images")
public class ImageResource extends AbstractResource {

    private static final MediaType JPG = new MediaType("image", "jpge");

    private final RecordSetProvider recordSets;

    @Inject
    ImageResource(final RecordSetProvider recordSets) {
        this.recordSets = recordSets;
    }

    @GET
    @Path("records/{id}/{file}")
    @Cached(maxAgeDays = 1)
    @Produces("image/*")
    public Response recordImage(
            @Context final UserSessionContext context,
            @PathParam("id") final RecordSetId id,
            @PathParam("file") final String filename) {
        if (filename.startsWith(".") || !filename.endsWith(".jpg") || filename.contains("/")) return notFound();
        final var recordSet = recordSets.get(id);
        if (recordSet == null) return notFound();
        return this.readSessionResource(context.session(), "/images/records/" + id.value() + "/" + filename, JPG);
    }

}
