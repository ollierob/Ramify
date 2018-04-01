package net.ramify.model.place;

import net.ramify.model.place.position.Position;

import javax.annotation.Nonnull;
import java.util.Optional;

public abstract class NamedLocation extends NamedPlace implements Location {

    private final Position position;

    protected NamedLocation(final String name) {
        this(name, null);
    }

    protected NamedLocation(final String name, final Position position) {
        super(name);
        this.position = position;
    }

    @Nonnull
    @Override
    public Optional<? extends Position> position() {
        return Optional.ofNullable(position);
    }

}
