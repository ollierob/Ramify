package net.ramify.model.event.collection;

import net.ramify.model.Has;
import net.ramify.model.event.Event;
import net.ramify.model.event.type.Birth;
import net.ramify.model.event.type.Death;

import javax.annotation.Nonnull;
import java.util.Set;

public interface HasEvents extends Has<Event> {

    @Nonnull
    Set<Event> events();

    @Override
    @Deprecated
    default Set<Event> values() {
        return this.events();
    }

    default boolean hasBirth() {
        return this.has(Birth.class);
    }

    default boolean hasDeath() {
        return this.has(Death.class);
    }

}
