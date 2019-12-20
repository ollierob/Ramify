package net.ramify.model.place.provider;

import net.ramify.model.place.PlaceGroup;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.util.provider.MissingValueException;
import net.ramify.model.util.provider.Provider;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

public interface PlaceGroupProvider extends Provider<PlaceGroupId, PlaceGroup> {

    @Nonnull
    @Override
    default PlaceGroup require(final PlaceGroupId id) {
        return this.requireOrThrow(id, UnknownPlaceGroupIdException::new);
    }

    @CheckForNull
    PlaceGroup getGroup(@Nonnull PlaceId placeId);

    @Nonnull
    default PlaceGroup requireGroup(final PlaceId placeId) {
        final var group = this.getGroup(placeId);
        if (group == null) throw new UnknownPlaceGroupIdException(placeId);
        return group;
    }

    class UnknownPlaceGroupIdException extends MissingValueException {

        UnknownPlaceGroupIdException(final PlaceGroupId id) {
            super("Unknown group: " + id);
        }

        UnknownPlaceGroupIdException(final PlaceId id) {
            super("Unknown group for place: " + id);
        }

    }

}
