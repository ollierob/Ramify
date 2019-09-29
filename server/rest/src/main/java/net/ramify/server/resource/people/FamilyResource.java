package net.ramify.server.resource.people;

import net.ramify.server.resource.Resource;

import javax.ws.rs.Path;

public interface FamilyResource extends Resource {

    @Path("trees")
    FamilyTreeResource trees();

}
