package net.ramify.forum.post.author;

import net.ollie.protobuf.BuildsProto;
import net.ramify.forum.proto.ForumProto;

import javax.annotation.Nonnull;

public interface Author extends BuildsProto<ForumProto.Author> {

    @Nonnull
    String name();

    @Nonnull
    default ForumProto.Author.Builder toProtoBuilder() {
        return ForumProto.Author.newBuilder()
                .setName(this.name());
    }

    @Override
    default ForumProto.Author toProto() {
        return this.toProtoBuilder().build();
    }

}
