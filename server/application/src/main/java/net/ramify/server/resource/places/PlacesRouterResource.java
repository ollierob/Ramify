package net.ramify.server.resource.places;

import net.ramify.authentication.NotLoggedInException;
import net.ramify.authentication.UserSessionContext;
import net.ramify.server.resource.AbstractResource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("places")
public class PlacesRouterResource extends AbstractResource {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response router(@Context final UserSessionContext context) throws NotLoggedInException {
        return this.readRouterResource(context.session(), "places");
    }

}
