package net.ramify.model.place.provider;

import net.ramify.model.Provider;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;

public interface PlaceProvider<P extends Place> extends Provider<PlaceId, P> {

}
