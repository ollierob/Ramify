package net.ramify.model.event.collection;

import net.ramify.model.event.type.UniqueEvent;
import net.ramify.model.person.HasPersonId;
import net.ramify.utils.collections.IterableUtils;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface HasPersonEvents extends HasEvents, HasPersonId {

    @Nonnull
    default <T extends UniqueEvent> Optional<T> find(final Class<T> type) {
        return IterableUtils.findFirst(this.events(), e -> e.is(type)).flatMap(e -> e.as(type));
    }

}
