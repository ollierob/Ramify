package net.ramify.model.event.type;

import net.ramify.model.event.PersonEvent;

public interface PostDeathEvent extends PersonEvent {

    @Override
    default <R> R handleWith(final EventHandler<R> handler) {
        return handler.handle(this);
    }

}
