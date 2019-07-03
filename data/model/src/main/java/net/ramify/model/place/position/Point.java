package net.ramify.model.place.position;

import net.ramify.data.proto.BuildsProto;
import net.ramify.model.place.proto.LocationProto;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Set;

public interface Point extends Position, BuildsProto<LocationProto.Point> {

    double latitude();

    double longitude();

    @Deprecated
    @Override
    default Set<Point> points() {
        return Collections.singleton(this);
    }

    @Override
    default boolean isNowhere() {
        return false;
    }

    @Override
    @Deprecated
    default boolean isPoint() {
        return true;
    }

    @Override
    default boolean isLine() {
        return false;
    }

    @Nonnull
    @Override
    default LocationProto.Point toProto() {
        return LocationProto.Point.newBuilder()
                .setLatitude(this.latitude())
                .setLongitude(this.longitude())
                .build();
    }

    @Nonnull
    static Point atLatLong(final double latitude, final double longitude) {
        return new Point() {

            @Override
            public double latitude() {
                return latitude;
            }

            @Override
            public double longitude() {
                return longitude;
            }
        };
    }

}
