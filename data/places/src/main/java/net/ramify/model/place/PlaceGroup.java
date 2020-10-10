package net.ramify.model.place;

import com.google.common.collect.Iterables;
import net.ramify.data.proto.BuildsProto;
import net.ramify.model.place.proto.PlaceProto;

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
    default Set<Place> children() {
        return this.children(true);
    }

    @Nonnull
    Set<Place> children(boolean includeDefault);

    @Nonnull
    default PlaceProto.PlaceGroup.Builder toProtoBuilder() {
        return PlaceProto.PlaceGroup.newBuilder()
                .setId(this.id().value())
                .setName(this.name())
                .setDefaultChild(this.defaultChild().toProto())
                .addAllOtherChildren(Iterables.transform(this.children(false), Place::toProto));
    }

    @Nonnull
    @Override
    default PlaceProto.PlaceGroup toProto() {
        return this.toProtoBuilder().build();
    }

}
