package net.ramify.model.place.position;

import com.google.common.collect.Iterables;
import net.ramify.data.proto.BuildsProto;
import net.ramify.model.place.proto.LocationProto;

import javax.annotation.Nonnull;
import java.util.Set;

public interface Area extends Position, BuildsProto<LocationProto.Area> {

    @Nonnull
    Set<Point> boundary();

    @Deprecated
    @Override
    default Set<Point> points() {
        return this.boundary();
    }

    @Nonnull
    @Override
    default LocationProto.Area toProto() {
        return LocationProto.Area.newBuilder()
                .addAllBoundary(Iterables.transform(this.boundary(), Point::toProto))
                .build();
    }

}
