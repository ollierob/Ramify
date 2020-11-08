package net.ramify.forum.topic.view;

import net.ramify.forum.post.Post;
import net.ramify.forum.proto.ViewProto;

import javax.annotation.Nonnull;

public interface TopicSummary extends TopicMeta {

    @Nonnull
    Post latestPost();

    @Nonnull
    @Override
    default ViewProto.TopicView.Builder toProtoBuilder() {
        return TopicMeta.super.toProtoBuilder()
                .setLatestPost(this.latestPost().toProto());
    }

}
