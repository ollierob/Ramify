package net.ramify.model.place.merge;

import net.ramify.model.place.Place;
import net.ramify.model.strategy.MergeResult;
import net.ramify.model.strategy.Merger;

import java.util.Objects;

public interface PlaceMerger extends Merger<Place, Place> {

    @Override
    default Place just(final Place from) {
        return from;
    }

    PlaceMerger NO_MERGE = (p1, p2) -> Objects.equals(p1, p2) ? MergeResult.ofNullable(p1) : null;

}
