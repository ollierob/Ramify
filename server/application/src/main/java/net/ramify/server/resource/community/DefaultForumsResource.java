package net.ramify.server.resource.community;

import net.ramify.forum.ForumView;
import net.ramify.forum.board.BoardId;
import net.ramify.forum.board.view.BoardView;
import net.ramify.forum.topic.TopicId;
import net.ramify.forum.topic.view.TopicView;

import javax.inject.Singleton;

@Singleton
public class DefaultForumsResource implements ForumsResource {

    @Override
    public ForumView forum() {
        throw new UnsupportedOperationException(); //TODO
    }

    @Override
    public BoardView board(BoardId id) {
        throw new UnsupportedOperationException(); //TODO
    }

    @Override
    public TopicView topic(TopicId id) {
        throw new UnsupportedOperationException(); //TODO
    }

}
