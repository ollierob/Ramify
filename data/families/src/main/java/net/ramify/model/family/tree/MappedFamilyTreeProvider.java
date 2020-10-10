package net.ramify.model.family.tree;

import com.google.common.collect.Maps;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public abstract class MappedFamilyTreeProvider implements FamilyTreeProvider {

    private final Map<FamilyTreeId, Optional<FamilyTree>> map = Maps.newConcurrentMap();

    @Nonnull
    @Override
    public Set<FamilyTreeId> allIds() {
        return map.keySet();
    }

    @CheckForNull
    @Override
    public FamilyTree get(final FamilyTreeId id) {
        return this.maybeGet(id).orElse(null);
    }

    @Nonnull
    @Override
    public Optional<FamilyTree> maybeGet(final FamilyTreeId id) {
        return map.computeIfAbsent(id, this::doCreate);
    }

    private Optional<FamilyTree> doCreate(final FamilyTreeId id) {
        return Optional.ofNullable(this.create(id));
    }

    @CheckForNull
    protected abstract FamilyTree create(FamilyTreeId id);

}
