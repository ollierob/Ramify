package net.ramify.model.place;

import net.ramify.model.place.type.HasCountry;
import net.ramify.model.place.type.PlaceHandler;

import javax.annotation.Nonnull;

public interface Place extends HasPlaceId, HasCountry {

    @Nonnull
    String name();

    boolean contains(@Nonnull Place that);

    <R> R handleWith(PlaceHandler<R> handler);

}
