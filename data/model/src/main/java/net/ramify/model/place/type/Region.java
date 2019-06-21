package net.ramify.model.place.type;

import net.ramify.model.place.Place;

import javax.annotation.Nonnull;

public interface Region extends Place {

    @Nonnull
    Country country();

}
