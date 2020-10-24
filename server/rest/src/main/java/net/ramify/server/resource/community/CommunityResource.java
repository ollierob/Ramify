package net.ramify.server.resource.community;

import net.ramify.server.resource.RootResource;

import javax.ws.rs.Path;

@Path("community")
public interface CommunityResource extends RootResource {

    @Path("groups")
    GroupsResource groups();

    @Path("forums")
    ForumsResource forums();

}
