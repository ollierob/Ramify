package net.ramify.forum.topic;

import com.google.common.collect.Iterables;
import net.ollie.protobuf.BuildsProto;
import net.ramify.forum.proto.ForumProto;

import javax.annotation.Nonnull;
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

    @Nonnull
    default ForumProto.Topic.Builder toProtoBuilder() {
        return ForumProto.Topic.newBuilder()
                .setId(this.id().value())
                .setName(this.name())
                .setPinned(this.isPinned())
                .setLocked(this.isLocked())
                .addAllSubTopic(Iterables.transform(this.subTopics(), Topic::toProto));
    }

    @Override
    default ForumProto.Topic toProto() {
        return this.toProtoBuilder().build();
    }

}
