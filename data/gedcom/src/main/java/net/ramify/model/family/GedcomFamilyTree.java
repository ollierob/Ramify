package net.ramify.model.family;

import net.ramify.model.family.tree.FamilyTree;
import net.ramify.model.family.tree.FamilyTreeInfo;

import javax.annotation.Nonnull;
import java.util.Set;

public class GedcomFamilyTree implements FamilyTree {

    @Nonnull
    @Override
    public FamilyTreeInfo info() {
        throw new UnsupportedOperationException(); //TODO
    }

    @Nonnull
    @Override
    public Set<? extends Family> families() {
        throw new UnsupportedOperationException(); //TODO
    }

}
