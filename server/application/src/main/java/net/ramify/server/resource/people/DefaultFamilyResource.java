package net.ramify.server.resource.people;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DefaultFamilyResource implements FamilyResource {

    private final FamilyTreeResource familyTreeResource;

    @Inject
    DefaultFamilyResource(final FamilyTreeResource familyTreeResource) {
        this.familyTreeResource = familyTreeResource;
    }

    @Override
    public FamilyTreeResource trees() {
        return familyTreeResource;
    }

}
