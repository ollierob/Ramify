package net.ramify.server.resource.community;

import net.ramify.forum.board.Board;
import net.ramify.forum.board.BoardId;
import net.ramify.server.resource.Resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

public interface ForumsResource extends Resource {

    @GET
    @Path("/boards/{id}")
    List<Board> boards(@PathParam("id") BoardId id);

}
