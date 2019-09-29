package net.ramify.server.resource.people;

import net.ramify.server.resource.RootResource;

import javax.ws.rs.Path;

@Path("people")
public interface PeopleResource extends RootResource {

    @Path("families")
    FamilyResource families();

}
