package net.ramify.model.family.tree;

import javax.annotation.Nonnull;

public class DefaultFamilyTreeInfo implements FamilyTreeInfo {

    private final FamilyTreeId id;
    private final String name;
    private final int numPeople;

    public DefaultFamilyTreeInfo(final FamilyTreeId id, final String name, final int numPeople) {
        this.id = id;
        this.name = name;
        this.numPeople = numPeople;
    }

    @Nonnull
    @Override
    public String name() {
        return name;
    }

    @Override
    public int numPeople() {
        return numPeople;
    }

    @Nonnull
    @Override
    public FamilyTreeId familyTreeId() {
        return id;
    }

}
