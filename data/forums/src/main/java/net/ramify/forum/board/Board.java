package net.ramify.forum.board;

import net.ollie.protobuf.BuildsProto;
import net.ramify.forum.proto.ForumProto;

import javax.annotation.Nonnull;

public interface Board extends BuildsProto<ForumProto.Board> {

    @Nonnull
    BoardId id();

    @Nonnull
    String name();

    @Nonnull
    String description();

    @Nonnull
    default ForumProto.Board.Builder toProtoBuilder() {
        return ForumProto.Board.newBuilder()
                .setId(this.id().value())
                .setName(this.name())
                .setDescription(this.description());
    }

    @Nonnull
    default ForumProto.Board toProto() {
        return this.toProtoBuilder().build();
    }

}
