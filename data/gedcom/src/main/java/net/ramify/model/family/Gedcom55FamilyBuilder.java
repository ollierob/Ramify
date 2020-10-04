package net.ramify.model.family;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.ramify.model.person.Person;

import java.util.Map;
import java.util.Set;

class Gedcom55FamilyBuilder implements GedcomFamilyLineReader {

    private final Map<String, Person> people = Maps.newHashMap();

    private String husband, wife;
    private Set<String> children = Sets.newHashSet();

    @Override
    public void read(final int level, final int start, final String line) {
        switch (level) {
            case 1:
                final var next = line.indexOf(' ', start);
                final var type = line.substring(start, next);
                switch (type) {
                    case "HUSB" -> husband = line.substring(next);
                    case "WIFE" -> wife = line.substring(next);
                    case "CHIL" -> children.add(line.substring(next));
                }
        }
    }

    void add(final Gedcom55PersonBuilder builder) {
        people.put(builder.id(), builder.build());
    }

    Family build() {
        return new FamilyOfUnknownRelationships(people.values());
    }

}
