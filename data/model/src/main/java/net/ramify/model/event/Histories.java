package net.ramify.model.event;

import net.ramify.model.person.Person;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Map;

public interface Histories {

    static Histories of(final Map<Person, PersonalEvents> events) {
        return () -> Collections.unmodifiableMap(events);
    }

    @Nonnull
    Map<Person, PersonalEvents> events();

}
