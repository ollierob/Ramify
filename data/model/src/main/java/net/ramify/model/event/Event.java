package net.ramify.model.event;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.type.Birth;
import net.ramify.model.event.type.Death;
import net.ramify.model.event.type.EventHandler;
import net.ramify.model.event.type.LifeEvent;
import net.ramify.model.event.type.PostDeathEvent;

import javax.annotation.Nonnull;

public interface Event extends HasEventId {

    @Nonnull
    DateRange date();

    <R> R handleWith(@Nonnull EventHandler<R> handler);

    default boolean isBirth() {
        return this instanceof Birth;
    }

    default boolean isLife() {
        return this instanceof LifeEvent;
    }

    default boolean isDeath() {
        return this instanceof Death;
    }

    default boolean isPostDeath() {
        return this instanceof PostDeathEvent;
    }

}
