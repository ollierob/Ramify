package net.ramify.model.event;

import net.ramify.utils.collections.Iterables;

import javax.annotation.Nonnull;
import java.util.Set;

public interface HasEvents {

    @Nonnull
    Set<Event> events();

    default boolean hasBirth() {
        return Iterables.any(this.events(), Event::isBirth);
    }

}
