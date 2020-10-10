package net.ramify.model.place.position;

import net.ollie.protobuf.BuildsProto;
import net.ramify.model.place.proto.LocationProto;

import javax.annotation.Nonnull;

public interface Point extends BuildsProto<LocationProto.Point> {

    double latitude();

    double longitude();

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
        return new DefaultPoint(latitude, longitude);
    }

}
