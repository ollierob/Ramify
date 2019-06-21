package net.ramify.model.place;

import javax.annotation.Nonnull;

public abstract class AbstractPlace implements Place {

    private final PlaceId id;
    private final String name;

    protected AbstractPlace(final PlaceId id, final String name) {
        this.id = id;
        this.name = name;
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

}
