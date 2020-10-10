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

@Path("js")
public class JavascriptResource extends AbstractResource {

    private static final MediaType MEDIA_TYPE = new MediaType("application", "javascript");

    @GET
    @Path("{file}")
    @Cached(maxAgeDays = 1)
    @Produces("application/javascript")
    public Response readJs(
            @Context final UserSessionContext context,
            @PathParam("file") final String file)
            throws NotLoggedInException {
        if (file.startsWith(".") || !file.endsWith(".js") || file.contains("/")) {
            return notFound();
        }
        return this.readSessionResource(context.session(), "/js/" + file, MEDIA_TYPE);
    }

}
