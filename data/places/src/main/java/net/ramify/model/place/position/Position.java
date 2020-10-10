package net.ramify.model.place.position;

import com.google.common.collect.Iterables;
import net.ollie.protobuf.BuildsProto;
import net.ramify.model.place.proto.LocationProto;

import javax.annotation.Nonnull;
import java.util.List;

public interface Position extends BuildsProto<LocationProto.Position> {

    @Nonnull
    Point center();

    @Nonnull
    List<Point> boundaryPoints();

    int zoom();

    default boolean isPoint() {
        return this.boundaryPoints().size() <= 1;
    }

    default boolean isLine() {
        return this.boundaryPoints().size() == 2;
    }

    default boolean isArea() {
        return this.boundaryPoints().size() >= 3;
    }

    @Nonnull
    @Override
    default LocationProto.Position toProto() {
        return LocationProto.Position.newBuilder()
                .setCenter(this.center().toProto())
                .addAllBoundary(Iterables.transform(this.boundaryPoints(), Point::toProto))
                .setZoom(this.zoom())
                .build();
    }

}
