package net.ramify.model.place;

import net.ramify.model.place.type.PlaceHandler;

public interface Place extends HasPlaceId {

    boolean contains(Place that);

    <R> R handleWith(PlaceHandler<R> handler);

}
