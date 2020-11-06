package net.ramify.forum.topic;

import net.ollie.protobuf.BuildsProto;
import net.ramify.forum.post.Post;
import net.ramify.forum.proto.ForumProto;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Set;

public interface Topic extends BuildsProto<ForumProto.Topic> {

    @Nonnull
    TopicId id();

    @Nonnull
    String name();

    boolean isPinned();

    boolean isLocked();

    @Nonnull
    Set<Topic> subTopics();

}
