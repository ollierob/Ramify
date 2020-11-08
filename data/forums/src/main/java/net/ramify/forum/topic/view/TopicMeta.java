package net.ramify.forum.topic.view;

import net.ollie.protobuf.BuildsProto;
import net.ramify.forum.proto.ViewProto;
import net.ramify.forum.topic.Topic;

import javax.annotation.Nonnull;

interface TopicMeta extends BuildsProto<ViewProto.TopicView> {

    @Nonnull
    Topic topic();

    int numViews();

    int numPosts();

    @Nonnull
    default ViewProto.TopicView.Builder toProtoBuilder() {
        return ViewProto.TopicView.newBuilder()
                .setTopic(this.topic().toProto())
                .setNumViews(this.numViews())
                .setNumPosts(this.numPosts());
    }

    @Override
    default ViewProto.TopicView toProto() {
        return this.toProtoBuilder().build();
    }

}
