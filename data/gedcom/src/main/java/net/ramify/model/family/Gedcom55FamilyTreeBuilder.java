package net.ramify.model.family;

import com.google.common.collect.Maps;
import net.ramify.model.family.tree.DefaultFamilyTreeInfo;
import net.ramify.model.family.tree.FamilyTree;
import net.ramify.model.family.tree.FamilyTreeId;
import net.ramify.model.family.tree.SingletonFamilyTree;
import net.ramify.utils.collections.SetUtils;

import java.util.Map;

class Gedcom55FamilyTreeBuilder implements GedcomFamilyTreeBuilder {

    private final Map<String, Gedcom55PersonBuilder> people = Maps.newHashMap();
    private final Map<String, Gedcom55FamilyBuilder> families = Maps.newHashMap();

    private final FamilyTreeId id;
    private GedcomFamilyLineReader current;

    Gedcom55FamilyTreeBuilder(final FamilyTreeId id) {
        this.id = id;
    }

    @Override
    public void read(final int level, final int start, final String line) {
        if (level == 0) {
            current = null;
            final var last = line.lastIndexOf(' ');
            final var prev = last > start ? line.substring(start, last) : null;
            final var type = line.substring(last + 1);
            switch (type) {
                case "INDI" -> this.handleNewIndividual(prev);
                case "SOUR" -> this.handleNewSource(prev);
                case "FAM" -> this.handleNewFamily(prev);
            }
        } else {
            if (current != null) current.read(level, start, line);
        }
    }

    private void handleNewIndividual(final String id) {
        final var builder = new Gedcom55PersonBuilder(id, this::addPersonToFamily);
        this.current = builder;
        people.put(id, builder);
    }

    private Gedcom55FamilyBuilder handleNewFamily(final String id) {
        final var builder = families.computeIfAbsent(id, i -> new Gedcom55FamilyBuilder());
        current = builder;
        return builder;
    }

    private void addPersonToFamily(final String familyId, final Gedcom55PersonBuilder builder) {
        families.computeIfAbsent(familyId, i -> new Gedcom55FamilyBuilder()).add(builder);
    }

    private void handleNewSource(final String id) {
        //TODO
    }

    @Override
    public FamilyTree build() {
        final var info = new DefaultFamilyTreeInfo(id, id.toString(), people.size());
        return switch (families.size()) {
            case 0 -> new SingletonFamilyTree(
                    info,
                    new FamilyOfUnknownRelationships(SetUtils.eagerTransform(people.values(), Gedcom55PersonBuilder::build)));
            case 1 -> new SingletonFamilyTree(
                    info,
                    families.values().iterator().next().build());
            default -> new SingletonFamilyTree(
                    info,
                    new DisjointFamilies(SetUtils.eagerTransform(families.values(), Gedcom55FamilyBuilder::build)));
        };
    }

}
