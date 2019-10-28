package net.ramify.server.resource.places;

import com.google.inject.AbstractModule;
import net.ramify.server.resource.places.churches.DefaultChurchesResource;

public class PlaceResourceModule extends AbstractModule {

    @Override
    protected void configure() {
        super.configure();
        this.bind(PlacesRouterResource.class);
        this.bind(ChurchesResource.class).to(DefaultChurchesResource.class);
        this.bind(DefaultPlacesResource.class);
        this.bind(PlaceFavouritesResource.class).to(DefaultPlaceFavouritesResource.class);
    }

}
