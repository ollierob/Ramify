package net.ramify.model.place;

import javax.annotation.Nonnull;
import java.util.Objects;

public abstract class AbstractPlace implements Place {

    private final PlaceId id;
    private final String name;

    protected AbstractPlace(final PlaceId id, final String name) {
        this.id = Objects.requireNonNull(id, "id");
        this.name = Objects.requireNonNull(name, "name");
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
