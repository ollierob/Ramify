package net.ramify.model.place.position;

import com.google.common.collect.Iterables;
import net.ramify.data.proto.BuildsProto;
import net.ramify.model.place.proto.LocationProto;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Set;

public interface Position extends BuildsProto<LocationProto.Position> {

    @Nonnull
    Point center();

    @Nonnull
    Set<Point> points();

    int zoom();

    default boolean isNowhere() {
        return this.points().isEmpty();
    }

    default boolean isPoint() {
        return this.points().size() == 1;
    }

    default boolean isLine() {
        return this.points().size() == 2;
    }

    @Nonnull
    @Override
    default LocationProto.Position toProto() {
        return LocationProto.Position.newBuilder()
                .setCenter(this.center().toProto())
                .addAllBoundary(Iterables.transform(this.points(), Point::toProto))
                .setZoom(this.zoom())
                .build();
    }

    static Position of(final Point point, final int zoom) {
        return new Position() {

            @Nonnull
            @Override
            public Point center() {
                return point;
            }

            @Nonnull
            @Override
            public Set<Point> points() {
                return Collections.emptySet();
            }

            @Override
            public int zoom() {
                return zoom;
            }
        };
    }

}
