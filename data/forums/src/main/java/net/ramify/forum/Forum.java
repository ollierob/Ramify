package net.ramify.forum;

import net.ollie.protobuf.BuildsProto;
import net.ramify.forum.proto.ForumProto;

import javax.annotation.Nonnull;

public interface Forum extends BuildsProto<ForumProto.Forum> {

    @Nonnull
    String name();

    @Nonnull
    default ForumProto.Forum.Builder toProtoBuilder() {
        return ForumProto.Forum.newBuilder()
                .setName(this.name());
    }

    @Override
    default ForumProto.Forum toProto() {
        return this.toProtoBuilder().build();
    }

}
