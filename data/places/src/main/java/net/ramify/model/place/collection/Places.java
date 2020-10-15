package net.ramify.model.place.collection;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import net.ollie.protobuf.BuildsProto;
import net.ramify.model.place.Place;
import net.ramify.model.place.proto.PlaceProto;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

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

    static Collector<Place, ?, Places> collector() {
        return new Collector<Place, Set<Place>, Places>() {

            @Override
            public Supplier<Set<Place>> supplier() {
                return HashSet::new;
            }

            @Override
            public BiConsumer<Set<Place>, Place> accumulator() {
                return Set::add;
            }

            @Override
            public BinaryOperator<Set<Place>> combiner() {
                return (s1, s2) -> {
                    s1.addAll(s2);
                    return s1;
                };
            }

            @Override
            public Function<Set<Place>, Places> finisher() {
                return Places::of;
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Collections.singleton(Characteristics.UNORDERED);
            }
            
        };
    }

}
