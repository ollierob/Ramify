package net.ramify.model.event;

import net.ramify.model.person.PersonId;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Map;

public interface Histories {

    static Histories of(final Map<PersonId, PersonalEvents> events) {
        return () -> Collections.unmodifiableMap(events);
    }

    @Nonnull
    Map<PersonId, PersonalEvents> events();

}
