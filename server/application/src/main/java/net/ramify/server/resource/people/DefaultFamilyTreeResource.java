package net.ramify.server.resource.people;

import net.ramify.model.family.tree.FamilyTree;
import net.ramify.model.family.tree.FamilyTreeId;
import net.ramify.model.family.tree.FamilyTreeProvider;
import net.ramify.model.family.tree.FamlyTreeMetas;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.stream.Collectors;

@Singleton
public class DefaultFamilyTreeResource implements FamilyTreeResource {

    private final FamilyTreeProvider familyTreeProvider;

    @Inject
    DefaultFamilyTreeResource(final FamilyTreeProvider familyTreeProvider) {
        this.familyTreeProvider = familyTreeProvider;
    }

    @Override
    public FamlyTreeMetas names() {
        return () -> familyTreeProvider.allMeta().collect(Collectors.toSet());
    }

    @Override
    public FamilyTree load(final FamilyTreeId id) {
        return familyTreeProvider.require(id);
    }

}
