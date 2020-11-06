package net.ramify.server.resource.community;

import net.ramify.forum.board.Board;
import net.ramify.forum.board.BoardId;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public class DefaultForumsResource implements ForumsResource {

    @Override
    public List<Board> boards(BoardId id) {
        throw new UnsupportedOperationException(); //TODO
    }

}
