package net.ramify.forum;

import com.google.common.collect.Lists;
import net.ollie.protobuf.BuildsProto;
import net.ramify.forum.board.view.BoardSummary;
import net.ramify.forum.proto.ViewProto;

import javax.annotation.Nonnull;
import java.util.List;

public interface ForumView extends BuildsProto<ViewProto.ForumView> {

    @Nonnull
    Forum forum();

    @Nonnull
    List<BoardSummary> boards();

    @Nonnull
    default ViewProto.ForumView.Builder toProtoBuilder() {
        return ViewProto.ForumView.newBuilder()
                .setForum(this.forum().toProto())
                .addAllBoard(Lists.transform(this.boards(), BoardSummary::toProto));
    }

    @Nonnull
    @Override
    default ViewProto.ForumView toProto() {
        return this.toProtoBuilder().build();
    }

}
