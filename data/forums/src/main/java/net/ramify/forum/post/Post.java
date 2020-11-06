package net.ramify.forum.post;

import net.ollie.protobuf.BuildsProto;
import net.ramify.forum.proto.ForumProto;

import javax.annotation.Nonnull;

public interface Post extends BuildsProto<ForumProto.Post> {

    @Nonnull
    PostId id();

    @Nonnull
    String message();

}
