package net.ramify.model.event.collection;

import net.ramify.model.event.Event;
import net.ramify.model.event.type.Birth;
import net.ramify.model.event.type.Death;
import net.ramify.utils.collections.IterableUtils;

import javax.annotation.Nonnull;
import java.util.Set;

public interface HasEvents {

    @Nonnull
    Set<? extends Event> events();

    default boolean has(final Class<? extends Event> type) {
        return IterableUtils.has(this.events(), type);
    }

    default boolean hasBirth() {
        return this.has(Birth.class);
    }

    default boolean hasDeath() {
        return this.has(Death.class);
    }

}
