package net.ramify.forum;

import net.ramify.forum.board.BoardId;
import net.ramify.forum.board.view.BoardView;
import net.ramify.forum.topic.TopicId;
import net.ramify.forum.topic.view.TopicView;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

public interface ForumViewProvider {

    @Nonnull
    ForumView forum();

    @CheckForNull
    BoardView board(BoardId id);

    @CheckForNull
    TopicView topic(TopicId id);

}
