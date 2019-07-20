package net.ramify.model.event.collection;

import com.google.common.collect.Sets;
import net.ramify.model.event.Event;
import net.ramify.model.event.type.Birth;
import net.ramify.model.event.type.Death;
import net.ramify.model.place.HasPlace;
import net.ramify.model.place.Place;
import net.ramify.model.place.collection.HasPlaces;
import net.ramify.utils.collections.IterableUtils;

import javax.annotation.Nonnull;
import java.util.Set;

public interface HasEvents extends HasPlaces {

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

    @Nonnull
    @Override
    default Set<? extends Place> places() {
        final var places = Sets.<Place>newHashSet();
        this.events().forEach(event -> {
            if (event instanceof HasPlace) {
                places.add(((HasPlace) event).place());
            }
        });
        return places;
    }
}
