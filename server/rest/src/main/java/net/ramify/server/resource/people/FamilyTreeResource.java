package net.ramify.server.resource.people;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.EventId;
import net.ramify.model.family.tree.FamilyTree;
import net.ramify.model.family.tree.FamilyTreeId;
import net.ramify.model.family.tree.FamilyTreeMeta;
import net.ramify.model.family.tree.FamlyTreeMetas;
import net.ramify.model.person.PersonId;
import net.ramify.server.resource.Resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Set;

import static net.ramify.server.resource.Resource.APPLICATION_PROTOBUF;

@Produces({MediaType.TEXT_PLAIN, APPLICATION_PROTOBUF})
public interface FamilyTreeResource extends Resource {

    @GET
    @Path("meta")
    FamlyTreeMetas names();

    @GET
    @Path("meta/{id}")
    FamilyTreeMeta name(@PathParam("id") FamilyTreeId id);

    @GET
    @Path("load/{id}")
    FamilyTree loadTree(@PathParam("id") FamilyTreeId id);

    @GET
    @Path("load/{treeId}/{personId}")
    FamilyTree loadPersonTree(
            @PathParam("treeId") FamilyTreeId id,
            @PathParam("personId") PersonId personId);

    @GET
    @Path("load/{treeId}/{personId}/infer/birth")
    DateRange inferBirth(
            @PathParam("treeId") FamilyTreeId id,
            @PathParam("personId") PersonId personId,
            @QueryParam("event") Set<EventId> eventIds);

}
