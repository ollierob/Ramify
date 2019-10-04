package net.ramify.server.resource.people;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
class DefaultPeopleResource implements PeopleResource {

    private final FamilyResource familyResource;

    @Inject
    DefaultPeopleResource(final FamilyResource familyResource) {
        this.familyResource = familyResource;
    }

    @Override
    public FamilyResource families() {
        return familyResource;
    }

}
