package net.ramify.model.event.type;

import net.ramify.model.event.PersonEvent;

public interface EventHandler<R> {

    default R handle(final PersonEvent event) {
        return event.handleWith(this);
    }

    R handle(BirthEvent birth);

    R handle(LifeEvent event);

    R handle(DeathEvent death);

    R handle(PostDeathEvent event);

}
