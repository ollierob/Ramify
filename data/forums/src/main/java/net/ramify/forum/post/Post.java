package net.ramify.forum.post;

import net.ollie.protobuf.BuildsProto;
import net.ramify.forum.post.author.Author;
import net.ramify.forum.proto.ForumProto;

import javax.annotation.Nonnull;

public interface Post extends BuildsProto<ForumProto.Post> {

    @Nonnull
    PostId id();

    @Nonnull
    Author author();

    @Nonnull
    String message();

    @Nonnull
    default ForumProto.Post.Builder toProtoBuilder() {
        return ForumProto.Post.newBuilder()
                .setId(this.id().value())
                .setAuthor(this.author().toProto())
                .setMessage(this.message());
    }

    @Override
    default ForumProto.Post toProto() {
        return this.toProtoBuilder().build();
    }

}
