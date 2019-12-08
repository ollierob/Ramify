package net.ramify.model.record.residence.uk;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Sets;
import net.ramify.model.date.ClosedDateRange;
import net.ramify.model.date.DateRange;
import net.ramify.model.date.ExactDate;
import net.ramify.model.event.Event;
import net.ramify.model.event.infer.MarriageConditionEventInference;
import net.ramify.model.event.type.birth.GenericBirth;
import net.ramify.model.event.type.death.GenericDeathEvent;
import net.ramify.model.event.type.residence.GenericResidenceEvent;
import net.ramify.model.family.Family;
import net.ramify.model.family.FamilyBuilder;
import net.ramify.model.person.AbstractPerson;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.age.Age;
import net.ramify.model.person.gender.Gender;
import net.ramify.model.person.gender.Sex;
import net.ramify.model.person.name.Name;
import net.ramify.model.place.Place;
import net.ramify.model.record.GenericRecordPerson;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.residence.CensusRecord;
import net.ramify.model.relationship.Relationship;
import net.ramify.model.relationship.RelationshipFactory;
import net.ramify.model.relationship.type.Married;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.time.Month;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class Census1851Record extends CensusRecord {

    public static final ExactDate CENSUS_DATE = ExactDate.on(1851, Month.MARCH, 30);

    private final Census1851Head head;
    private final List<Census1851Resident> residents;

    public Census1851Record(
            final RecordId id,
            final RecordSet recordSet,
            final Place place,
            final Census1851Head head,
            final List<Census1851Resident> residents) {
        super(id, recordSet, CENSUS_DATE, place);
        this.head = head;
        this.residents = residents;
    }

    PersonId headId() {
        return head.id();
    }

    @Nonnull
    @Override
    public Family family() {
        final var head = this.head.build(this);
        final var builder = new FamilyBuilder(head);
        this.widowOf(head).ifPresent(dead -> builder.addRelationship(head, dead, Married::new));
        residents.forEach(entry -> {
            final var person = entry.build(this);
            builder.addRelationship(head, person, entry.relationshipToHead());
            this.widowOf(person).ifPresent(dead -> builder.addRelationship(person, dead, Married::new));
        });
        return builder.build();
    }

    @CheckForNull
    private Optional<Person> widowOf(final Census1851Person person) {
        if (!person.isWidowed()) return Optional.empty();
        final var id = PersonId.random();
        return Optional.of(new GenericRecordPerson(
                id,
                Name.UNKNOWN,
                person.gender().inverse(),
                Collections.singleton(new GenericDeathEvent(id, ClosedDateRange.of(person.birthDate, CENSUS_DATE))), //FIXME tighter bounds
                "Inferred ex-spouse of " + person.name()));
    }

    public static abstract class Census1851Entry {

        private final PersonId id;
        private final Name name;
        private final Sex sex;
        private final RelationshipFactory relationshipToHead;
        private final MarriageConditionEventInference condition;
        private final Age age;
        private final Place birthPlace;

        protected Census1851Entry(
                final PersonId id,
                final Name name,
                final Sex sex,
                final RelationshipFactory relationshipToHead,
                final MarriageConditionEventInference condition,
                final Age age,
                final Place birthPlace) {
            this.id = id;
            this.name = name;
            this.sex = sex;
            this.relationshipToHead = relationshipToHead;
            this.condition = MoreObjects.firstNonNull(condition, MarriageConditionEventInference.NONE);
            this.age = age;
            this.birthPlace = birthPlace;
        }

        public PersonId id() {
            return id;
        }

        RelationshipFactory relationshipToHead() {
            return relationshipToHead;
        }

        Census1851Person build(final Census1851Record record) {
            final var id = this.id();
            return new Census1851Person(
                    id,
                    name,
                    sex,
                    record.place(),
                    relationshipToHead.relationshipBetween(record.headId(), id),
                    age.birthDate(CENSUS_DATE),
                    birthPlace,
                    condition.inferEvents(id, record),
                    condition);
        }

    }

    public static class Census1851Head extends Census1851Entry {

        public Census1851Head(
                final PersonId id,
                final Name name,
                final Sex sex,
                final MarriageConditionEventInference condition,
                final Age age,
                final Place birthPlace) {
            super(id, name, sex, (a, b) -> null, condition, age, birthPlace);
        }

    }

    public static class Census1851Resident extends Census1851Entry {

        public Census1851Resident(
                final PersonId id,
                final Name name,
                final Sex sex,
                final RelationshipFactory relationshipToHead,
                final MarriageConditionEventInference condition,
                final Age age,
                final Place birthPlace) {
            super(id, name, sex, Objects.requireNonNull(relationshipToHead), condition, age, birthPlace);
        }

    }

    public static class Census1851Person extends AbstractPerson {

        private final Place residencePlace;
        private final DateRange birthDate;
        private final Place birthPlace;
        private final Relationship relationshipToHead;
        private final Set<? extends Event> extraEvents;
        private final MarriageConditionEventInference condition;

        Census1851Person(
                final PersonId id,
                final Name name,
                final Gender gender,
                final Place residencePlace,
                final Relationship relationshipToHead,
                final DateRange birthDate,
                final Place birthPlace,
                final Set<? extends Event> extraEvents,
                final MarriageConditionEventInference condition) {
            super(id, name, gender);
            this.residencePlace = Objects.requireNonNull(residencePlace, "residence place");
            this.birthDate = birthDate;
            this.birthPlace = birthPlace;
            this.relationshipToHead = relationshipToHead;
            this.extraEvents = extraEvents;
            this.condition = condition;
        }

        boolean isHead() {
            return relationshipToHead == null;
        }

        boolean isWidowed() {
            return condition != null && condition.isWidowed();
        }

        @Nonnull
        @Override
        public Set<? extends Event> events() {
            final var events = Sets.newHashSet(
                    new GenericBirth(this.personId(), birthDate).with(birthPlace),
                    new GenericResidenceEvent(this.personId(), CENSUS_DATE, residencePlace));
            events.addAll(extraEvents);
            return events;
        }

    }

}
