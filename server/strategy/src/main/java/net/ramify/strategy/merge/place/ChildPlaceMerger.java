package net.ramify.strategy.merge.place;

import net.ramify.model.place.Place;
import net.ramify.model.place.merge.PlaceMerger;

import javax.annotation.Nonnull;

public class ChildPlaceMerger implements PlaceMerger {

    private final PlaceMerger fallback;

    public ChildPlaceMerger(final PlaceMerger fallback) {
        this.fallback = fallback;
    }

    @Nonnull
    @Override
    public Result<Place> merge(final Place p1, final Place p2) {
//        if (p1.isChildOf(p2)) return Result.of(p1);
//        if (p2.isChildOf(p1)) return Result.of(p2);
//        return fallback.merge(p1, p2);
        throw new UnsupportedOperationException();
    }

}
