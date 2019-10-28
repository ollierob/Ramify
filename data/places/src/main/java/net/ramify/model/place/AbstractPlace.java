package net.ramify.model.place;

import net.ramify.model.place.region.Country;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Objects;

public abstract class AbstractPlace implements Place {

    private final PlaceId id;
    private final String name;
    private final PlaceGroupId groupId;

    protected AbstractPlace(final PlaceId id, final String name, PlaceGroupId groupId) {
        this.id = Objects.requireNonNull(id, "id");
        this.name = Objects.requireNonNull(name, "name");
        this.groupId = groupId;
    }

    @Nonnull
    @Override
    public PlaceId placeId() {
        return id;
    }

    @Nonnull
    @Override
    public String name() {
        return name;
    }

    @CheckForNull
    @Override
    public PlaceGroupId groupId() {
        return groupId;
    }

    @Override
    public boolean isDefunct() {
        return DefunctPlaces.isDefunct(this, this.find(Country.class).orElse(null));
    }

}
