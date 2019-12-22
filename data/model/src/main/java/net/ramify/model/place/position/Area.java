package net.ramify.model.place.position;

import javax.annotation.Nonnull;
import java.util.Set;

public interface Area extends Position {

    @Nonnull
    Set<Point> boundary();

    @Deprecated
    @Override
    default Set<Point> boundaryPoints() {
        return this.boundary();
    }

    @Nonnull
    @Override
    default Point center() {
        throw new UnsupportedOperationException(); //TODO compute
    }

}
