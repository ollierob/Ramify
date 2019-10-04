package net.ramify.server.resource.people;

import com.google.inject.AbstractModule;

public class PeopleResourceModule extends AbstractModule {

    @Override
    protected void configure() {
        super.configure();
        this.bind(PeopleRouterResource.class);
        this.bind(PeopleResource.class).to(DefaultPeopleResource.class);
        this.bind(FamilyResource.class).to(DefaultFamilyResource.class);
        this.bind(FamilyTreeResource.class).to(DefaultFamilyTreeResource.class);
    }

}
