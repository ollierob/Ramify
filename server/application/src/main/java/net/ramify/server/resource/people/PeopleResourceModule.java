package net.ramify.server.resource.people;

import com.google.inject.AbstractModule;

public class PeopleResourceModule extends AbstractModule {

    @Override
    protected void configure() {
        super.configure();
        this.bind(PeopleRouterResource.class);
    }

}
