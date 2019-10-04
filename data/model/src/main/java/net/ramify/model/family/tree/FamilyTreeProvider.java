package net.ramify.model.family.tree;

import net.ramify.model.Provider;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public interface FamilyTreeProvider extends Provider<FamilyTreeId, FamilyTree> {

    @Nonnull
    Set<FamilyTreeId> allIds();

    @Nonnull
    default Stream<FamilyTreeMeta> allMeta() {
        return this.allIds()
                .stream()
                .map(this::get)
                .filter(Objects::nonNull)
                .map(FamilyTree::meta);
    }

}
