package net.ramify.model.family;

import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;
import net.ramify.model.relationship.Relationship;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class DisjointFamilies implements Family {

    public static Family of(final Set<Family> families) {
        switch (families.size()) {
            case 1:
                return families.iterator().next();
            default:
                return new DisjointFamilies(families);
        }
    }

    private final Set<Family> families;

    public DisjointFamilies(final Set<Family> families) {
        this.families = families;
    }

    @Nonnull
    @Override
    public Person root() {
        return families.size() == 1 ? families.iterator().next().root() : null;
    }

    @Nonnull
    @Override
    public Set<? extends Person> people() {
        return families.stream().map(Family::people).flatMap(Collection::stream).collect(Collectors.toSet());
    }

    @Nonnull
    @Override
    public Set<? extends Relationship> relationships() {
        return families.stream().map(Family::relationships).flatMap(Collection::stream).collect(Collectors.toSet());
    }

    @Nonnull
    @Override
    public Optional<Relationship> relationshipBetween(final PersonId from, final PersonId to) {
        return families.stream().map(f -> f.relationshipBetween(from, to).orElse(null)).findAny();
    }

}
