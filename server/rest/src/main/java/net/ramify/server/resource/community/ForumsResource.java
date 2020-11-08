package net.ramify.server.resource.community;

import net.ramify.forum.ForumView;
import net.ramify.forum.board.BoardId;
import net.ramify.forum.board.view.BoardView;
import net.ramify.forum.topic.TopicId;
import net.ramify.forum.topic.view.TopicView;
import net.ramify.server.resource.Resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

public interface ForumsResource extends Resource {

    @GET
    ForumView forum();

    @GET
    @Path("board/{id}")
    BoardView board(@PathParam("id") BoardId id);

    @GET
    @Path("topic/{id}")
    TopicView topic(@PathParam("id") TopicId id);

}
