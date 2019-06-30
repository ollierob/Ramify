package net.ramify.server.resource;

import com.google.inject.AbstractModule;
import net.ramify.server.resource.core.JavascriptResource;
import net.ramify.server.resource.places.PlaceResourceModule;

public class ResourceModule extends AbstractModule {

    @Override
    protected void configure() {
        super.configure();
        this.install(new PlaceResourceModule());
        this.bind(JavascriptResource.class);
    }
}
