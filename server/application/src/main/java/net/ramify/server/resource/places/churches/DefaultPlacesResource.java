package net.ramify.server.resource.places.churches;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.collection.Places;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.server.resource.places.ChurchesResource;
import net.ramify.server.resource.places.PlacesResource;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DefaultPlacesResource implements PlacesResource {

    private final PlaceProvider placeProvider;
    private final ChurchesResource churchesResource;

    @Inject
    DefaultPlacesResource(final PlaceProvider placeProvider, final ChurchesResource churchesResource) {
        this.placeProvider = placeProvider;
        this.churchesResource = churchesResource;
    }

    @Override
    public Place at(final PlaceId id) {
        return placeProvider.get(id);
    }

    @Override
    public Places within(final PlaceId id, final int depth) {
        return Places.of(placeProvider.children(id, depth), false);
    }

    @Override
    public ChurchesResource churches() {
        return churchesResource;
    }

}
