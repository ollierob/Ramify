package net.ramify.model.event.type;

import net.ramify.model.event.Event;

public interface EventHandler<R> {

    default R handle(final Event event) {
        return event.handleWith(this);
    }

    R handle(Birth birth);

    R handle(LifeEvent event);

    R handle(Death death);

    R handle(PostDeathEvent event);

}
