package net.ramify.model.event;

import net.ramify.model.Castable;
import net.ramify.model.date.DateRange;
import net.ramify.model.event.type.Birth;
import net.ramify.model.event.type.Death;
import net.ramify.model.event.type.EventHandler;
import net.ramify.model.event.type.LifeEvent;
import net.ramify.model.event.type.PostDeathEvent;

import javax.annotation.Nonnull;

public interface Event extends HasEventId, Castable<Event> {

    @Nonnull
    DateRange date();

    <R> R handleWith(@Nonnull EventHandler<R> handler);

    default boolean isBirth() {
        return this.is(Birth.class);
    }

    default boolean isLife() {
        return this.is(LifeEvent.class);
    }

    default boolean isDeath() {
        return this.is(Death.class);
    }

    default boolean isPostDeath() {
        return this.is(PostDeathEvent.class);
    }

}
