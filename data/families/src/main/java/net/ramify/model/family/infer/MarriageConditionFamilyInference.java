package net.ramify.model.family.infer;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.ramify.model.date.BeforeDate;
import net.ramify.model.event.Event;
import net.ramify.model.event.EventId;
import net.ramify.model.event.infer.MarriageConditionEventInference;
import net.ramify.model.event.type.death.GenericDeathEvent;
import net.ramify.model.person.PersonId;
import net.ramify.model.record.Record;
import net.ramify.model.relationship.Relationship;
import net.ramify.model.relationship.type.Married;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface MarriageConditionFamilyInference {

    @Nonnull
    Optional<Inference> inferFamily(PersonId personId, Record record, SpouseLookup lookup);

    MarriageConditionFamilyInference UNMARRIED = (p, r, f) -> Optional.empty();

    MarriageConditionFamilyInference MARRIED = (person, record, f) -> {
        final var spouse = f.spouseOf(person);
        final var relationship = new Married(person, spouse);
        final var events = ArrayListMultimap.<PersonId, Event>create();
        events.putAll(person, MarriageConditionEventInference.MARRIED.inferEvents(person, record));
        events.putAll(spouse, MarriageConditionEventInference.MARRIED.inferEvents(person, record));
        return Optional.of(new Inference(relationship, events));
    };

    MarriageConditionFamilyInference WIDOWED = (person, record, f) -> {
        final var deceased = f.createSpouse();
        final var relationship = new Married(person, deceased);
        final var date = BeforeDate.strictlyBefore(record.date());
        final var events = ArrayListMultimap.<PersonId, Event>create();
        events.putAll(person, MarriageConditionEventInference.WIDOWED.inferEvents(person, record));
        events.putAll(deceased, MarriageConditionEventInference.WIDOWED.inferEvents(deceased, record));
        events.put(deceased, new GenericDeathEvent(EventId.random(), deceased, date));
        return Optional.of(new Inference(relationship, events));
    };

    interface SpouseLookup {

        @Nonnull
        PersonId spouseOf(PersonId personId);

        @Nonnull
        PersonId createSpouse();

    }

    class Inference {

        private final Relationship relationship;
        private final Multimap<PersonId, Event> events;

        public Inference(
                final Relationship relationship,
                final Multimap<PersonId, Event> events) {
            this.events = events;
            this.relationship = relationship;
        }

    }

}
