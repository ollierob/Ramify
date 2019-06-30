package net.ramify.server.resource.places;

import com.google.inject.AbstractModule;
import net.ramify.model.place.xml.XmlPlaceModule;
import net.ramify.server.resource.places.churches.DefaultChurchesResource;
import net.ramify.server.resource.places.churches.DefaultPlacesResource;

public class PlaceResourceModule extends AbstractModule {

    @Override
    protected void configure() {
        super.configure();
        this.install(new XmlPlaceModule());
        this.bind(PlaceRouterResource.class);
        this.bind(ChurchesResource.class).to(DefaultChurchesResource.class);
        this.bind(DefaultPlacesResource.class);
    }

}
