package net.ramify.model.family.tree;

import net.ramify.model.family.Family;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Set;

public class SingletonFamilyTree implements FamilyTree {

    private final FamilyTreeMeta meta;
    private final Family family;

    public SingletonFamilyTree(final FamilyTreeMeta meta, final Family family) {
        this.meta = meta;
        this.family = family;
    }

    @Nonnull
    @Override
    public FamilyTreeMeta meta() {
        return meta;
    }

    @Nonnull
    @Override
    public Set<? extends Family> families() {
        return Collections.singleton(family);
    }

}
