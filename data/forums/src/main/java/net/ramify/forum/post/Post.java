package net.ramify.forum.post;

import javax.annotation.Nonnull;

public interface Post {

    @Nonnull
    PostId id();

    @Nonnull
    String message();

}
