package net.ramify.forum.board;

import net.ollie.protobuf.BuildsProto;
import net.ramify.forum.proto.ForumProto;

import javax.annotation.Nonnull;
import java.util.Set;

public interface Board extends BuildsProto<ForumProto.Board> {

    @Nonnull
    BoardId id();

    @Nonnull
    String name();

    @Nonnull
    Set<Board> subBoards();

}
