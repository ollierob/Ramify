package net.ramify.model.family.tree;

import net.ramify.model.util.provider.Provider;

import javax.annotation.Nonnull;
import java.util.Set;

public interface FamilyTreeMetaProvider extends Provider<FamilyTreeId, FamilyTreeMeta> {

    @Nonnull
    Set<FamilyTreeMeta> all();

}
