package net.ramify.model.event;

import net.ramify.model.person.Person;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Life events for a single person.
 *
 * @see net.ramify.model.family.relationship.PersonalRelationships
 */
public interface PersonalEvents extends Events {

    static PersonalEvents of(final Person person, final Event event) {
        return of(person, Set.of(event));
    }

    @Nonnull
    default Optional<Birth> birth() {
        return this.earliest(Birth.class);
    }

    @Nonnull
    default Optional<Death> death() {
        return this.latest(Death.class);
    }

    static PersonalEvents of(final Person person, final Set<Event> events) {
        return events.isEmpty()
                ? none(person)
                : new PersonalEvents() {

            @Nonnull
            @Override
            public Person person() {
                return person;
            }

            @Nonnull
            @Override
            public Set<Event> events() {
                return Collections.unmodifiableSet(events);
            }
        };
    }

    static PersonalEvents of(final Person person, final Event event, final Set<Event> otherEvents) {
        final Set<Event> events = new HashSet<>(otherEvents);
        events.add(event);
        return of(person, events);
    }

    static PersonalEvents none(final Person person) {
        return new PersonalEvents() {

            @Nonnull
            @Override
            public Person person() {
                return person;
            }

            @Nonnull
            @Override
            public Set<Event> events() {
                return Collections.emptySet();
            }
        };
    }

    @Nonnull
    Person person();

    default PersonalEvents and(final Event event) {
        return of(this.person(), event, this.events());
    }

}
