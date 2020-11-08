package net.ramify.forum.topic.view;

import com.google.common.collect.Lists;
import net.ollie.protobuf.BuildsProto;
import net.ramify.forum.post.Post;
import net.ramify.forum.proto.ViewProto;
import net.ramify.forum.topic.Topic;

import javax.annotation.Nonnull;
import java.util.List;

public interface TopicView extends BuildsProto<ViewProto.TopicView> {

    @Nonnull
    Topic topic();

    @Nonnull
    List<Post> posts();

    int numViews();

    int numPosts();

    @Nonnull
    default ViewProto.TopicView.Builder toProtoBuilder() {
        return ViewProto.TopicView.newBuilder()
                .setTopic(this.topic().toProto())
                .setNumViews(this.numViews())
                .setNumPosts(this.numPosts())
                .addAllPost(Lists.transform(this.posts(), Post::toProto));
    }

    @Override
    default ViewProto.TopicView toProto() {
        return this.toProtoBuilder().build();
    }

}
