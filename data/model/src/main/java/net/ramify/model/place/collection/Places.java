package net.ramify.model.place.collection;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import net.ramify.data.proto.BuildsProto;
import net.ramify.model.place.Place;
import net.ramify.model.place.proto.PlaceProto;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

public interface Places extends Iterable<Place>, HasPlaces, BuildsProto<PlaceProto.PlaceList> {

    @Nonnull
    @Override
    default Set<? extends Place> places() {
        return Sets.newHashSet(this);
    }

    @Nonnull
    @Override
    default PlaceProto.PlaceList toProto() {
        return PlaceProto.PlaceList.newBuilder()
                .addAllPlace(Iterables.transform(this, Place::toProto))
                .build();
    }

    static Places of() {
        return of(ImmutableSet.of());
    }

    static Places of(final Collection<? extends Place> places) {
        final ImmutableSet<Place> fixedPlaces = ImmutableSet.copyOf(places);
        return new Places() {

            @Override
            public Iterator<Place> iterator() {
                return fixedPlaces.iterator();
            }

            @Nonnull
            @Override
            public ImmutableSet<Place> places() {
                return fixedPlaces;
            }

        };
    }

    Places NONE = Collections::emptyIterator;

}
