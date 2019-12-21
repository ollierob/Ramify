package net.ramify.model.place;

import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.region.Country;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Objects;

public abstract class AbstractPlace implements Place {

    private final PlaceId id;
    private final String name;
    private final PlaceGroupId groupId;
    private final PlaceHistory history;

    protected AbstractPlace(final PlaceId id, final String name, final PlaceGroupId groupId, final PlaceHistory history) {
        this.id = Objects.requireNonNull(id, "id");
        this.name = Objects.requireNonNull(name, "name");
        this.groupId = groupId;
        this.history = history;
    }

    @Nonnull
    @Override
    public PlaceId placeId() {
        return id;
    }

    @Nonnull
    @Override
    public PlaceGroupId placeGroupId() {
        return groupId == null ? Place.super.placeGroupId() : groupId;
    }

    @Nonnull
    @Override
    public String name() {
        return name;
    }

    @CheckForNull
    @Override
    public PlaceHistory history() {
        return history;
    }

    @Override
    public boolean isDefunct() {
        return (history != null && history.hasClosure()) || DefunctPlaces.isDefunct(this, this.find(Country.class).orElse(null));
    }

    protected static PlaceProto.PlaceType.Builder placeTypeBuilder(final String name) {
        return PlaceProto.PlaceType.newBuilder()
                .setName(name)
                .setCanPrefix(true)
                .setCanSuffix(true);
    }

    protected static PlaceProto.PlaceType placeType(final String name) {
        return placeTypeBuilder(name).build();
    }

}
