package net.ramify.server.resource.core;

import net.ramify.authentication.UserSessionContext;
import net.ramify.server.resource.AbstractResource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("js")
public class JavascriptResource extends AbstractResource {

    @GET
    @Path("{file}")
    @Produces("application/javascript")
    public Response readJs(
            @Context final UserSessionContext context,
            @PathParam("file") final String file) {
        if (file.startsWith(".") || !file.endsWith(".js") || file.contains("/")) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return this.readSessionResource(context.session(), "/js/" + file);
    }

}
