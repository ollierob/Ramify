package net.ramify.model.place.position;

import javax.annotation.Nonnull;
import java.util.Set;

public interface Position {

    @Nonnull
    Set<Point> points();

    default boolean isNowhere() {
        return this.points().isEmpty();
    }

    default boolean isPoint() {
        return this.points().size() == 1;
    }

    default boolean isLine() {
        return this.points().size() == 2;
    }

}
