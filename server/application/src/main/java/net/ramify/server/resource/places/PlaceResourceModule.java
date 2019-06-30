package net.ramify.server.resource.places;

import com.google.inject.AbstractModule;
import net.ramify.model.place.xml.XmlPlaceModule;
import net.ramify.server.resource.places.churches.DefaultChurchesResource;

public class PlaceResourceModule extends AbstractModule {

    @Override
    protected void configure() {
        super.configure();
        this.install(new XmlPlaceModule());
        this.bind(PlaceRouterResource.class);
        this.bind(DefaultChurchesResource.class);
    }

}
