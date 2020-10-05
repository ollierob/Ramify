package net.ramify.model.family;

import com.google.common.collect.Maps;
import net.ramify.model.date.parse.DateRangeParser;
import net.ramify.model.family.tree.DefaultFamilyTreeInfo;
import net.ramify.model.family.tree.FamilyTree;
import net.ramify.model.family.tree.FamilyTreeId;
import net.ramify.model.family.tree.SingletonFamilyTree;
import net.ramify.model.place.provider.PlaceParser;
import net.ramify.utils.collections.SetUtils;

import java.util.Map;

class Gedcom55FamilyTreeBuilder implements GedcomFamilyTreeBuilder {

    private final Map<String, Gedcom55PersonBuilder> people = Maps.newHashMap();
    private final Map<String, Gedcom55FamilyBuilder> families = Maps.newHashMap();
    private final DateRangeParser dateParser;
    private final PlaceParser placeParser;
    private final FamilyTreeId id;
    private GedcomFamilyLineReader current;

    Gedcom55FamilyTreeBuilder(final DateRangeParser dateParser, final PlaceParser placeParser, final FamilyTreeId id) {
        this.dateParser = dateParser;
        this.placeParser = placeParser;
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

    private void handleNewIndividual(final String personId) {
        current = people.computeIfAbsent(personId, id -> new Gedcom55PersonBuilder(dateParser, placeParser, id, this::addPersonToFamily));
    }

    private void handleNewFamily(final String id) {
        current = families.computeIfAbsent(id, i -> new Gedcom55FamilyBuilder());
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
        if (families.isEmpty()) {
            final var people = SetUtils.eagerTransform(this.people.values(), Gedcom55PersonBuilder::build);
            return new SingletonFamilyTree(info, new FamilyOfUnknownRelationships(people));
        } else {
            final var builder = new FamilyBuilder();
            families.values().forEach(family -> family.addTo(builder));
            return new SingletonFamilyTree(info, builder.build());
        }
    }

}
