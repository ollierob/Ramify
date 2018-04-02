package net.ramify.model.family;

import net.ramify.model.family.relationship.Relationship;
import net.ramify.model.person.Person;

import javax.annotation.Nonnull;
import java.util.Set;

public class SinglePersonFamily implements Family {

    private final Person person;

    public SinglePersonFamily(final Person person) {
        this.person = person;
    }

    @Nonnull
    @Override
    public Set<Relationship> relationships() {
        return Set.of();
    }

    @Nonnull
    @Override
    public Set<Person> people() {
        return Set.of(person);
    }

}
