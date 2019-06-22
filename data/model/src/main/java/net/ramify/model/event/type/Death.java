package net.ramify.model.event.type;

import javax.annotation.Nonnull;

public interface Death extends UniqueEvent {

    @Override
    default <R> R handleWith(final EventHandler<R> handler) {
        return handler.handle(this);
    }

    @Nonnull
    @Override
    default String title() {
        return "Death";
    }

}