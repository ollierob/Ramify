package net.ramify.model.place;

import net.ramify.model.Has;

import javax.annotation.Nonnull;
import java.util.Set;

public interface HasPlaces extends Has<Place> {

    @Nonnull
    Set<? extends Place> places();

    @Nonnull
    @Override
    default Set<? extends Place> values() {
        return this.places();
    }

}
