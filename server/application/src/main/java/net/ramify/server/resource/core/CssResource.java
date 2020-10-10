package net.ramify.server.resource.core;

import net.ramify.authentication.NotLoggedInException;
import net.ramify.authentication.UserSessionContext;
import net.ramify.server.resource.AbstractResource;
import net.ramify.server.resource.Cached;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("css")
public class CssResource extends AbstractResource {

    private static final MediaType MEDIA_TYPE = new MediaType("text", "css");

    @GET
    @Path("{file}")
    @Cached(maxAgeDays = 1)
    @Produces("text/css")
    public Response readCss(
            @Context final UserSessionContext context,
            @PathParam("file") final String file)
            throws NotLoggedInException {
        if (file.startsWith(".") || !file.endsWith(".css") || file.contains("/")) {
            return notFound();
        }
        return this.readSessionResource(context.session(), "/css/" + file, MEDIA_TYPE);
    }

}
