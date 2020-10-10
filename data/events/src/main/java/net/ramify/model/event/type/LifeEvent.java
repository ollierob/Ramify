package net.ramify.model.event.type;

import net.ramify.model.event.PersonEvent;

public interface LifeEvent extends PersonEvent {

    @Override
    default <R> R handleWith(final EventHandler<R> handler) {
        return handler.handle(this);
    }

}
