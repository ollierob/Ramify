package net.ramify.model.place;

import com.google.common.collect.Iterables;
import net.ramify.data.proto.BuildsProto;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.utils.collections.SetUtils;

import javax.annotation.Nonnull;
import java.util.Set;

public interface PlaceGroup extends BuildsProto<PlaceProto.PlaceGroup> {

    @Nonnull
    PlaceGroupId id();

    @Nonnull
    String name();

    @Nonnull
    Place defaultChild();

    @Nonnull
    Set<Place> children();

    @Nonnull
    default PlaceProto.PlaceGroup.Builder toProtoBuilder() {
        final var otherChildren = SetUtils.without(this.children(), this.defaultChild());
        return PlaceProto.PlaceGroup.newBuilder()
                .setId(this.id().value())
                .setName(this.name())
                .setDefaultChild(this.defaultChild().toProto())
                .addAllOtherChildren(Iterables.transform(otherChildren, Place::toProto));
    }

    @Nonnull
    @Override
    default PlaceProto.PlaceGroup toProto() {
        return this.toProtoBuilder().build();
    }

}
