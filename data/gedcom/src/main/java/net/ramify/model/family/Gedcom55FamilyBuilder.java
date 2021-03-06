package net.ramify.model.family;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.ramify.model.relationship.type.Married;
import net.ramify.model.relationship.type.ParentChild;
import net.ramify.model.relationship.type.Unknown;
import net.ramify.utils.collections.MapUtils;

import java.util.Map;
import java.util.Set;

class Gedcom55FamilyBuilder implements GedcomFamilyLineReader {

    private final Map<String, Gedcom55PersonBuilder> peopleBuilders = Maps.newHashMap();

    private String father, mother;
    private boolean married;
    private Set<String> children = Sets.newHashSet();

    @Override
    public void read(final int level, final int start, final String line) {
        switch (level) {
            case 1:
                final var next = line.indexOf(' ', start);
                final var type = next <= 0 ? line.substring(start) : line.substring(start, next);
                switch (type) {
                    case "HUSB" -> father = line.substring(next + 1);
                    case "WIFE" -> mother = line.substring(next + 1);
                    case "CHIL" -> children.add(line.substring(next + 1));
                    case "MARR" -> married = true;
                }
        }
    }

    void add(final Gedcom55PersonBuilder builder) {
        peopleBuilders.put(builder.id(), builder);
    }

    void addTo(final FamilyBuilder builder) {
        //Make sure people are in the tree
        final var people = MapUtils.eagerlyTransformValues(peopleBuilders, Gedcom55PersonBuilder::build);
        people.values().forEach(builder::addPerson);
        //Build relationships
        final var father = this.father == null ? null : people.get(this.father);
        final var mother = this.mother == null ? null : people.get(this.mother);
        if (father != null && mother != null) builder.addRelationship(father, mother, married ? Married::new : Unknown::new);
        children.forEach(childId -> {
            final var child = people.get(childId);
            if (child == null) return;
            if (father != null) builder.addRelationship(father, child, ParentChild::new);
            if (mother != null) builder.addRelationship(mother, child, ParentChild::new);
        });
    }

}
