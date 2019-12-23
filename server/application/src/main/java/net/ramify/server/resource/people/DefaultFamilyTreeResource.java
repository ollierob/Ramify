package net.ramify.server.resource.people;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.EventId;
import net.ramify.model.family.tree.FamilyTree;
import net.ramify.model.family.tree.FamilyTreeId;
import net.ramify.model.family.tree.FamilyTreeInfo;
import net.ramify.model.family.tree.FamilyTreeProvider;
import net.ramify.model.family.tree.FamlyTreeMetas;
import net.ramify.model.person.PersonId;
import net.ramify.strategy.inference.date.BirthDateInference;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class DefaultFamilyTreeResource implements FamilyTreeResource {

    private final FamilyTreeProvider familyTreeProvider;
    private final BirthDateInference birthDateInference;

    @Inject
    DefaultFamilyTreeResource(final FamilyTreeProvider familyTreeProvider, final BirthDateInference birthDateInference) {
        this.familyTreeProvider = familyTreeProvider;
        this.birthDateInference = birthDateInference;
    }

    @Override
    public FamlyTreeMetas names() {
        return () -> familyTreeProvider.allMeta().collect(Collectors.toSet());
    }

    @Override
    public FamilyTree loadTree(final FamilyTreeId id) {
        return familyTreeProvider.require(id);
    }

    @Override
    public FamilyTreeInfo name(final FamilyTreeId id) {
        return this.loadTree(id).info();
    }

    @Override
    public FamilyTree loadPersonTree(final FamilyTreeId id, final PersonId personId) {
        return familyTreeProvider.require(id, personId);
    }

    @Override
    public DateRange inferBirth(final FamilyTreeId treeId, final PersonId personId, final Set<EventId> eventIds) {
        final var person = familyTreeProvider.require(treeId, personId).find(personId).orElse(null);
        if (person == null) return null;
        final var events = eventIds.isEmpty() ? person.events() : person.events().filteredCopy(event -> eventIds.contains(event.eventId()));
        return birthDateInference.infer(events).orElse(null);
    }

}
