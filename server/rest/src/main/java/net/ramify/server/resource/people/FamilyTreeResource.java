package net.ramify.server.resource.people;

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
import javax.ws.rs.core.MediaType;

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
    FamilyTree load(@PathParam("id") FamilyTreeId id);

    @GET
    @Path("load/{treeId}/{personId}")
    FamilyTree loadPerson(
            @PathParam("treeId") FamilyTreeId id,
            @PathParam("personId") PersonId personId);

}
