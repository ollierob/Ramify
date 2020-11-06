package net.ramify.forum.post;

import net.ramify.forum.topic.TopicId;
import net.ramify.model.util.provider.Provider;

import javax.annotation.Nonnull;
import java.util.List;

public interface PostProvider extends Provider<PostId, Post> {

    @Nonnull
    List<Post> in(TopicId topicId, int start, int end);

}
