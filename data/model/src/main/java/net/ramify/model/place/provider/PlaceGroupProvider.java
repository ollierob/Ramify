package net.ramify.model.place.provider;

import net.ramify.model.Provider;
import net.ramify.model.place.PlaceGroup;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;

import javax.annotation.Nonnull;

public interface PlaceGroupProvider extends Provider<PlaceGroupId, PlaceGroup> {

    @Nonnull
    PlaceGroup groupFor(@Nonnull PlaceId placeId);

}
