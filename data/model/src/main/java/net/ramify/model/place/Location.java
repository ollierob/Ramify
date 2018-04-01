package net.ramify.model.place;

import net.ramify.model.place.position.Position;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface Location extends Place {

    @Nonnull
    Optional<? extends Position> position();

}
