package net.ramify.forum.topic.view;

import net.ramify.forum.post.Post;
import net.ramify.forum.proto.ViewProto;

import javax.annotation.Nonnull;
import java.util.List;

public interface TopicView extends TopicMeta {

    @Nonnull
    List<Post> posts();

    default ViewProto.TopicView.PostView toViewProto() {
        return ViewProto.TopicView.PostView.newBuilder()
                .build();
    }

    @Nonnull
    @Override
    default ViewProto.TopicView.Builder toProtoBuilder() {
        return TopicMeta.super.toProtoBuilder()
                .setPosts(this.toViewProto());
    }

}
