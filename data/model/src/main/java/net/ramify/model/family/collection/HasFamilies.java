package net.ramify.model.family.collection;

import com.google.common.collect.Sets;
import net.ramify.model.family.Family;
import net.ramify.model.person.Person;
import net.ramify.model.person.collection.HasPeople;

import javax.annotation.Nonnull;
import java.util.Set;

public interface HasFamilies extends HasPeople {

    @Nonnull
    Set<? extends Family> families();

    @Nonnull
    @Override
    default Set<? extends Person> people() {
        final var people = Sets.<Person>newHashSet();
        this.families().forEach(family -> people.addAll(family.people()));
        return people;
    }

}
