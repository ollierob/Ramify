package net.ramify.model.place.collection;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import net.ramify.data.proto.BuildsProto;
import net.ramify.model.place.Place;
import net.ramify.model.place.proto.PlaceProto;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

public interface Places extends Iterable<Place>, HasPlaces, BuildsProto<PlaceProto.PlaceList> {

    @Nonnull
    @Override
    default Set<? extends Place> places() {
        return Sets.newHashSet(this);
    }

    default boolean protoIncludesParent() {
        return true;
    }

    @Nonnull
    @Override
    default PlaceProto.PlaceList toProto() {
        return PlaceProto.PlaceList.newBuilder()
                .addAllPlace(Iterables.transform(this, place -> place.toProto(this.protoIncludesParent())))
                .build();
    }

    static Places of(final Set<? extends Place> places, final boolean includeParent) {
        final ImmutableSet<Place> fixedPlaces = ImmutableSet.copyOf(places);
        return new Places() {

            @Override
            public boolean protoIncludesParent() {
                return includeParent;
            }

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
