package net.ramify.model.event.collection;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.ramify.model.event.PersonEvent;
import net.ramify.model.event.type.BirthEvent;
import net.ramify.model.event.type.DeathEvent;
import net.ramify.model.event.type.EventComparator;
import net.ramify.model.place.HasPlace;
import net.ramify.model.place.Place;
import net.ramify.model.place.collection.HasPlaces;
import net.ramify.utils.collections.IterableUtils;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public interface HasEvents extends HasPlaces {

    @Nonnull
    Set<? extends PersonEvent> events();

    @Nonnull
    default <E extends PersonEvent> Set<E> findEvents(final Class<E> eventType) {
        return IterableUtils.findAll(this.events(), eventType);
    }

    @Nonnull
    default List<? extends PersonEvent> sortedEvents() {
        return this.sortedEvents(EventComparator.DEFAULT);
    }

    @Nonnull
    default List<? extends PersonEvent> sortedEvents(final Comparator<? super PersonEvent> comparator) {
        final var list = Lists.newArrayList(this.events());
        list.sort(comparator);
        return list;
    }

    default boolean has(final Class<? extends PersonEvent> type) {
        return IterableUtils.has(this.events(), type);
    }

    default boolean hasBirth() {
        return this.has(BirthEvent.class);
    }

    default boolean hasDeath() {
        return this.has(DeathEvent.class);
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
