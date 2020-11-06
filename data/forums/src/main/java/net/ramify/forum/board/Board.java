package net.ramify.forum.board;

import com.google.common.collect.Iterables;
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
    String description();

    @Nonnull
    Set<Board> subBoards();

    @Nonnull
    default ForumProto.Board.Builder toProtoBuilder() {
        return ForumProto.Board.newBuilder()
                .setId(this.id().value())
                .setName(this.name())
                .setDescription(this.description())
                .addAllSubBoard(Iterables.transform(this.subBoards(), Board::toProto));
    }

    @Nonnull
    default ForumProto.Board toProto() {
        return this.toProtoBuilder().build();
    }

}
