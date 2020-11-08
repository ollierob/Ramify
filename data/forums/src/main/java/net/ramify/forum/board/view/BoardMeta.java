package net.ramify.forum.board.view;

import net.ollie.protobuf.BuildsProto;
import net.ramify.forum.board.Board;
import net.ramify.forum.proto.ViewProto;

import javax.annotation.Nonnull;

interface BoardMeta extends BuildsProto<ViewProto.BoardView> {

    @Nonnull
    Board board();

    int numPosts();

    int numTopics();

    @Nonnull
    default ViewProto.BoardView.Builder toProtoBuilder() {
        return ViewProto.BoardView.newBuilder()
                .setBoard(this.board().toProto())
                .setNumPosts(this.numPosts())
                .setNumTopics(this.numTopics());
    }

    @Override
    default ViewProto.BoardView toProto() {
        return this.toProtoBuilder().build();
    }

}
