package net.ramify.model.place.xml;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.provider.PlaceProvider;

import javax.annotation.CheckForNull;

public class XmlPlaceProvider implements PlaceProvider<Place> {

    @CheckForNull
    @Override
    public Place get(final PlaceId id) {
        throw new UnsupportedOperationException(); //TODO
    }

}
