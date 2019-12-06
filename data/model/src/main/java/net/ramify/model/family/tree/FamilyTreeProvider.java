package net.ramify.model.family.tree;

import net.ramify.model.util.Provider;
import net.ramify.model.person.PersonId;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public interface FamilyTreeProvider extends Provider<FamilyTreeId, FamilyTree> {

    @Nonnull
    Set<FamilyTreeId> allIds();

    default FamilyTree get(final FamilyTreeId treeId, final PersonId personId) {
        return this.get(treeId); //TODO
    }

    @Nonnull
    default Stream<FamilyTreeMeta> allMeta() {
        return this.allIds()
                .stream()
                .map(this::get)
                .filter(Objects::nonNull)
                .map(FamilyTree::meta);
    }

}
