package net.ramify.model.place;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

public interface PlaceOrGroupId {

    @CheckForNull
    PlaceId placeId();

    @Nonnull
    PlaceGroupId placeGroupId();

}
