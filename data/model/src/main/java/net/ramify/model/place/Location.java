package net.ramify.model.place;

import net.ramify.model.place.position.Position;

import javax.annotation.Nonnull;

public interface Location extends Place {

    @Nonnull
    Position position();

}
