package net.ramify.model.record;

import net.ramify.model.family.Family;
import net.ramify.model.person.Person;
import net.ramify.model.person.event.PersonalEvents;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Set;

public interface Record {

    @Nonnull
    Map<Person, PersonalEvents> events();

    @Nonnull
    Family family();

    default Set<Person> people() {
        return this.events().keySet(); //And people in family
    }

}
