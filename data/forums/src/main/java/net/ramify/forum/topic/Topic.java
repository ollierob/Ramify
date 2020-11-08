package net.ramify.forum.topic;

import net.ollie.protobuf.BuildsProto;
import net.ramify.forum.proto.ForumProto;

import javax.annotation.Nonnull;

public interface Topic extends BuildsProto<ForumProto.Topic> {

    @Nonnull
    TopicId id();

    @Nonnull
    String name();

    boolean isPinned();

    boolean isLocked();

    @Nonnull
    default ForumProto.Topic.Builder toProtoBuilder() {
        return ForumProto.Topic.newBuilder()
                .setId(this.id().value())
                .setName(this.name())
                .setPinned(this.isPinned())
                .setLocked(this.isLocked());
    }

    @Override
    default ForumProto.Topic toProto() {
        return this.toProtoBuilder().build();
    }

}
