package net.ramify.model.event.type;

import net.ramify.model.event.Event;

public interface LifeEvent extends Event {

    @Override
    default <R> R handleWith(final EventHandler<R> handler) {
        return handler.handle(this);
    }

}
