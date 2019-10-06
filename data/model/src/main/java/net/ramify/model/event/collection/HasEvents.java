package net.ramify.model.event.collection;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.ramify.model.event.Event;
import net.ramify.model.event.type.Birth;
import net.ramify.model.event.type.Death;
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
    Set<? extends Event> events();

    @Nonnull
    default List<? extends Event> sortedEvents() {
        return this.sortedEvents(EventComparator.DEFAULT);
    }

    @Nonnull
    default List<? extends Event> sortedEvents(final Comparator<? super Event> comparator) {
        final var list = Lists.newArrayList(this.events());
        list.sort(comparator);
        return list;
    }

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
