package net.ramify.model.place.provider;

import com.google.common.collect.Sets;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroup;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.util.provider.MissingValueException;
import net.ramify.model.util.provider.Provider;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Set;

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

    PlaceGroup synthesize(Place defaultPlace, Set<Place> otherChildren);

    default PlaceGroup getGroupOrSynthesize(final PlaceGroupId groupId, final PlaceProvider placeProvider) {
        final var group = this.get(groupId);
        if (group != null) return group;
        final var forGroup = placeProvider.findByGroup(groupId).iterator();
        if (!forGroup.hasNext()) return null;
        return this.synthesize(forGroup.next(), Sets.newHashSet(forGroup));
    }

    default PlaceGroup getGroupOrSynthesize(PlaceId placeId, PlaceProvider placeProvider) {
        final var group = this.getGroup(placeId);
        if (group != null) return group;
        final var place = placeProvider.require(placeId);
        return this.synthesize(place, Collections.emptySet());
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
