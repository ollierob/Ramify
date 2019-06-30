package net.ramify.server.resource.places;

import net.ramify.server.resource.RootResource;

import javax.ws.rs.Path;

@Path("places")
public interface PlacesResource extends RootResource {

    @Path("churches")
    ChurchesResource churches();

}
