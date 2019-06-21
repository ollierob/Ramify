package net.ramify.model.family;

import net.ramify.model.person.PersonId;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.stream.Stream;

public interface Families {

    @Nonnull
    Set<Family> families();

    default Stream<PersonId> people() {
        return this.families().stream().flatMap(Family::peopleStream);
    }

    static Families of() {
        return Set::of;
    }

    static Families of(final Family family) {
        return () -> Set.of(family);
    }

}
