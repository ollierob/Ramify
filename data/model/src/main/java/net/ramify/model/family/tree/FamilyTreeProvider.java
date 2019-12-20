package net.ramify.model.family.tree;

import net.ramify.model.util.MissingValueException;
import net.ramify.model.util.Provider;
import net.ramify.model.person.PersonId;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public interface FamilyTreeProvider extends Provider<FamilyTreeId, FamilyTree> {

    @Nonnull
    Set<FamilyTreeId> allIds();

    @Nonnull
    @Override
    default FamilyTree require(final FamilyTreeId key) {
        return this.requireOrThrow(key, UnknownTreeException::new);
    }

    @CheckForNull
    default FamilyTree get(final FamilyTreeId treeId, final PersonId personId) {
        final var tree = this.get(treeId);
        return tree == null ? null : tree.relativesOf(personId);
    }

    @Nonnull
    default FamilyTree require(final FamilyTreeId treeId, final PersonId personId) {
        return this.require(treeId).relativesOf(personId);
    }

    @Nonnull
    default Stream<FamilyTreeMeta> allMeta() {
        return this.allIds()
                .stream()
                .map(this::get)
                .filter(Objects::nonNull)
                .map(FamilyTree::meta);
    }

    class UnknownTreeException extends MissingValueException {

        UnknownTreeException(final FamilyTreeId id) {
            super("Unknown family tree: " + id);
        }

    }

}
