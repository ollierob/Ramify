package net.ramify.model.place;

import com.google.common.collect.Iterables;
import net.ramify.data.proto.BuildsProto;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.utils.collections.SetUtils;

import javax.annotation.Nonnull;
import java.util.Set;

public interface PlaceGroup extends BuildsProto<PlaceProto.PlaceGroup> {

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

    @Nonnull
    default PlaceProto.PlaceGroup.Builder toProtoBuilder() {
        final var otherChildIds = SetUtils.without(this.childIds(), this.defaultChildId());
        return PlaceProto.PlaceGroup.newBuilder()
                .setId(this.id().value())
                .setName(this.name())
                .setDefaultChildId(this.defaultChildId().value())
                .addAllOtherChildId(Iterables.transform(otherChildIds, PlaceId::value));
    }

    @Nonnull
    @Override
    default PlaceProto.PlaceGroup toProto() {
        return this.toProtoBuilder().build();
    }

}
