package net.ramify.server.resource.places.churches;

import net.ramify.server.resource.places.ChurchesResource;
import net.ramify.server.resource.places.PlacesResource;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DefaultPlacesResource implements PlacesResource {

    private final ChurchesResource churchesResource;

    @Inject
    DefaultPlacesResource(ChurchesResource churchesResource) {
        this.churchesResource = churchesResource;
    }

    @Override
    public ChurchesResource churches() {
        return churchesResource;
    }

}
