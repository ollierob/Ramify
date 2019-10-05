package net.ramify.model.place.collection;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import net.ramify.data.proto.BuildsProto;
import net.ramify.model.Id;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.utils.collections.SetUtils;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Set;

public interface PlaceIds extends Iterable<PlaceId>, BuildsProto<PlaceProto.PlaceIdList> {

    @Nonnull
    default Set<PlaceId> placeIds() {
        return Sets.newHashSet(this);
    }

    @Nonnull
    @Override
    default PlaceProto.PlaceIdList toProto() {
        return PlaceProto.PlaceIdList.newBuilder()
                .addAllId(Iterables.transform(this, Id::value))
                .build();
    }

    static PlaceIds of(final Collection<PlaceId> placeIds) {
        final var set = SetUtils.asSet(placeIds);
        return set::iterator;
    }

}
