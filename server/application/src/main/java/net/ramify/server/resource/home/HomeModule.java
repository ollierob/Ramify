package net.ramify.server.resource.home;

import com.google.inject.AbstractModule;

public class HomeModule extends AbstractModule {

    @Override
    protected void configure() {
        super.configure();
        this.bind(HomeResource.class);
    }

}
