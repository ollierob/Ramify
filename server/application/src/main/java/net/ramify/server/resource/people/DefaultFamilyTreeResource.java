package net.ramify.server.resource.people;

import net.ramify.model.family.tree.FamilyTree;
import net.ramify.model.family.tree.FamilyTreeId;
import net.ramify.model.family.tree.FamilyTreeMeta;
import net.ramify.model.family.tree.FamilyTreeProvider;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;
import java.util.stream.Collectors;

@Singleton
public class DefaultFamilyTreeResource implements FamilyTreeResource {

    private final FamilyTreeProvider familyTreeProvider;

    @Inject
    DefaultFamilyTreeResource(final FamilyTreeProvider familyTreeProvider) {
        this.familyTreeProvider = familyTreeProvider;
    }

    @Override
    public Collection<FamilyTreeMeta> names() {
        return familyTreeProvider.allMeta().collect(Collectors.toList());
    }

    @Override
    public FamilyTree load(final FamilyTreeId id) {
        return familyTreeProvider.require(id);
    }

}
