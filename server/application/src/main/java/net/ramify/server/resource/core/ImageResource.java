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

import static net.ramify.utils.StringUtils.isEmpty;

@Singleton
@Path("images")
public class ImageResource extends AbstractResource {

    private static final MediaType JPG = new MediaType("image", "jpg");
    private static final MediaType PNG = new MediaType("image", "png");
    private static final MediaType SVG = new MediaType("image", "svg+xml");

    private final RecordSetProvider recordSets;

    @Inject
    ImageResource(final RecordSetProvider recordSets) {
        this.recordSets = recordSets;
    }

    @GET
    @Path("flags/{flag}")
    @Produces("image/*")
    public Response flagImage(
            @Context final UserSessionContext context,
            @PathParam("flag") String flag) {
        if (isEmpty(flag)) return notFound();
        //TODO cache
        {
            if (flag.endsWith(".svg")) flag = flag.substring(0, flag.length() - 4);
            final var svg = this.webrootImage(context, "flags/" + flag + ".svg");
            if (svg.getStatus() == 200) return svg;
        }
        {
            if (flag.endsWith(".png")) flag = flag.substring(0, flag.length() - 4);
            final var png = this.webrootImage(context, "flags/" + flag + ".png");
            if (png.getStatus() == 200) return png;
        }
        {
            if (flag.endsWith(".jpg")) flag = flag.substring(0, flag.length() - 4);
            final var jpg = this.webrootImage(context, "flags/" + flag + ".jpg");
            if (jpg.getStatus() == 200) return jpg;
        }
        return null;
    }

    Response webrootImage(final UserSessionContext context, String path) {
        if (isEmpty(path)) return notFound();
        path = path.trim();
        if (path.startsWith(".") || path.length() < 3) return notFound();
        switch (path.substring(path.length() - 3)) {
            case "jpg":
                return this.readSessionResource(context.session(), "/webroot/images/" + path, JPG);
            case "png":
                return this.readSessionResource(context.session(), "/webroot/images/" + path, PNG);
            case "svg":
                return this.readSessionResource(context.session(), "/webroot/images/" + path, SVG);
            default:
                return notFound();
        }
    }

    @GET
    @Path("{file}")
    @Produces("image/*")
    @Cached(maxAgeDays = 1)
    public Response generalImage(
            @Context final UserSessionContext context,
            @PathParam("file") String filename) {
        if (isEmpty(filename)) return notFound();
        filename = filename.trim();
        if (filename.startsWith(".") || filename.length() < 3) return notFound();
        switch (filename.substring(filename.length() - 3)) {
            case "jpg":
                return this.readSessionResource(context.session(), "/images/" + filename, JPG);
            case "png":
                return this.readSessionResource(context.session(), "/images/" + filename, PNG);
            case "svg":
                return this.readSessionResource(context.session(), "/images/" + filename, SVG);
            default:
                return notFound();
        }
    }

    @GET
    @Path("records/{id}/{group}/{file}")
    @Cached(maxAgeDays = 1)
    @Produces("image/*")
    public Response recordImage(
            @Context final UserSessionContext context,
            @PathParam("id") final RecordSetId id,
            @PathParam("group") final String group,
            @PathParam("file") final String filename) {
        if (filename.startsWith(".") || !filename.endsWith(".jpg") || filename.contains("/")) return notFound();
        final var recordSet = recordSets.get(id);
        if (recordSet == null) return notFound();
        return this.readSessionResource(context.session(), "/images/records/" + id.value() + '/' + (group == null ? "" : group + '/') + filename, JPG);
    }

}
