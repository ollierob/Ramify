package net.ramify.model.place.position;

import javax.annotation.Nonnull;
import java.util.Set;

public interface Area {

    @Nonnull
    Set<Point> boundary();

    default boolean isNowhere() {
        return this.boundary().isEmpty();
    }

    default boolean isPoint() {
        return this.boundary().size() == 1;
    }

    default boolean isLine() {
        return this.boundary().size() == 2;
    }

}
