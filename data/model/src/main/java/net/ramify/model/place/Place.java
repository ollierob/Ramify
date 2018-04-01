package net.ramify.model.place;

import net.ramify.model.Castable;

import javax.annotation.Nonnull;

public interface Place extends Castable<Place> {

    @Nonnull
    String name();

    Place UNKNOWN = () -> "Unknown";

}
