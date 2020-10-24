package net.ramify.server.resource;

import com.google.inject.AbstractModule;
import net.ramify.server.resource.core.CssResource;
import net.ramify.server.resource.core.ImageResource;
import net.ramify.server.resource.core.JavascriptResource;
import net.ramify.server.resource.events.EventsResourceModule;
import net.ramify.server.resource.home.HomeModule;
import net.ramify.server.resource.jaxrs.JaxrsModule;
import net.ramify.server.resource.people.PeopleResourceModule;
import net.ramify.server.resource.places.PlaceResourceModule;
import net.ramify.server.resource.records.RecordsResourceModule;

public class ResourceModule extends AbstractModule {

    @Override
    protected void configure() {
        super.configure();
        this.install(new JaxrsModule());
        this.install(new PlaceResourceModule());
        this.install(new RecordsResourceModule());
        this.install(new PeopleResourceModule());
        this.install(new EventsResourceModule());
        this.install(new HomeModule());
        this.bind(CssResource.class);
        this.bind(JavascriptResource.class);
        this.bind(ImageResource.class);
    }

}
