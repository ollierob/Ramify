package net.ramify.model.place;

import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.utils.collections.SetUtils;

import javax.annotation.Nonnull;
import java.util.Set;

public interface PlaceGroup {

    @Nonnull
    PlaceGroupId id();

    @Nonnull
    String name();

    @Nonnull
    PlaceId defaultChildId();

    @Nonnull
    Set<PlaceId> childIds();

    @Nonnull
    default Set<Place> children(final PlaceProvider places) {
        return SetUtils.transformIgnoreNull(this.childIds(), places::require);
    }

}
