package net.ramify.model.record.census.uk;

import com.google.common.collect.Sets;
import net.ramify.model.date.DateRange;
import net.ramify.model.date.ExactDate;
import net.ramify.model.event.Event;
import net.ramify.model.event.infer.MarriageConditionEventInference;
import net.ramify.model.event.type.civil.GenericBirth;
import net.ramify.model.event.type.residence.GenericResidence;
import net.ramify.model.family.Family;
import net.ramify.model.person.AbstractPerson;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.age.Age;
import net.ramify.model.person.gender.Gender;
import net.ramify.model.person.gender.Sex;
import net.ramify.model.person.name.Name;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.census.CensusRecord;
import net.ramify.model.relationship.Relationship;

import javax.annotation.Nonnull;
import java.time.Month;
import java.time.Period;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Census1851Record extends CensusRecord {

    public static final ExactDate CENSUS_DATE = ExactDate.on(1851, Month.MARCH, 30);

    private final Census1851Head head;
    private final List<Census1851Resident> residents;

    public Census1851Record(final RecordId id, final PlaceId placeId, final Census1851Head head, final List<Census1851Resident> residents) {
        super(id, CENSUS_DATE, placeId);
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
        throw new UnsupportedOperationException(); //TODO
    }

    public static abstract class Census1851Entry {

        private final PersonId id;
        private final Name name;
        private final Sex sex;
        private final RelationshipToHead relationshipToHead;
        private final MarriageConditionEventInference condition;
        private final Period age;
        private final PlaceId birthPlace;

        protected Census1851Entry(
                final PersonId id,
                final Name name,
                final Sex sex,
                final RelationshipToHead relationshipToHead,
                final MarriageConditionEventInference condition,
                final Period age,
                final PlaceId birthPlace) {
            this.id = id;
            this.name = name;
            this.sex = sex;
            this.relationshipToHead = relationshipToHead;
            this.condition = condition;
            this.age = age;
            this.birthPlace = birthPlace;
        }

        public PersonId id() {
            return id;
        }

        Census1851Person build(final Census1851Record record) {
            return new Census1851Person(
                    id,
                    name,
                    sex,
                    record.placeId(),
                    relationshipToHead.relationship(record.headId(), id),
                    Age.exactly(age).birthDate(CENSUS_DATE),
                    birthPlace,
                    condition.inferEvents(id, record));
        }

    }

    public static class Census1851Head extends Census1851Entry {

        public Census1851Head(
                final PersonId id,
                final Name name,
                final Sex sex,
                final MarriageConditionEventInference condition,
                final Period age,
                final PlaceId birthPlace) {
            super(id, name, sex, (a, b) -> null, condition, age, birthPlace);
        }

    }

    public static class Census1851Resident extends Census1851Entry {

        public Census1851Resident(
                final PersonId id,
                final Name name,
                final Sex sex,
                final RelationshipToHead relationshipToHead,
                final MarriageConditionEventInference condition,
                final Period age,
                final PlaceId birthPlace) {
            super(id, name, sex, Objects.requireNonNull(relationshipToHead), condition, age, birthPlace);
        }

    }

    public static class Census1851Person extends AbstractPerson {

        private final PlaceId residencePlace;
        private final DateRange birthDate;
        private final PlaceId birthPlace;
        private final Relationship relationshipToHead;
        private final Set<? extends Event> extraEvents;

        Census1851Person(
                final PersonId id,
                final Name name,
                final Gender gender,
                final PlaceId residencePlace,
                final Relationship relationshipToHead,
                final DateRange birthDate,
                final PlaceId birthPlace,
                final Set<? extends Event> extraEvents) {
            super(id, name, gender);
            this.residencePlace = residencePlace;
            this.birthDate = birthDate;
            this.birthPlace = birthPlace;
            this.relationshipToHead = relationshipToHead;
            this.extraEvents = extraEvents;
        }

        boolean isHead() {
            return relationshipToHead == null;
        }

        @Nonnull
        @Override
        public Set<? extends Event> events() {
            final var events = Sets.newHashSet(
                    new GenericBirth(this.personId(), birthDate).with(birthPlace),
                    new GenericResidence(this.personId(), CENSUS_DATE, residencePlace));
            events.addAll(extraEvents);
            return events;
        }

    }

}
