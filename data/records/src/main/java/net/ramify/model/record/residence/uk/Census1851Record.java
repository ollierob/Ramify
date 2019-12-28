package net.ramify.model.record.residence.uk;

import com.google.common.base.MoreObjects;
import net.ramify.model.date.ExactDate;
import net.ramify.model.event.PersonEvent;
import net.ramify.model.event.collection.MutablePersonEventSet;
import net.ramify.model.event.collection.PersonEventSet;
import net.ramify.model.event.collection.SingletonPersonEventSet;
import net.ramify.model.event.infer.MarriageConditionEventInference;
import net.ramify.model.event.merge.UniqueEventMerger;
import net.ramify.model.family.Family;
import net.ramify.model.family.FamilyBuilder;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.age.Age;
import net.ramify.model.person.feature.Disability;
import net.ramify.model.person.features.PersonFeature;
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

import static net.ramify.utils.StringUtils.isBlank;

public class Census1851Record extends CensusRecord {

    public static final ExactDate CENSUS_DATE = ExactDate.on(1851, Month.MARCH, 30);

    private final Census1851Head head;
    private final List<Census1851Resident> residents;
    private final UniqueEventMerger eventMerger;

    public Census1851Record(
            final RecordId id,
            final RecordSet recordSet,
            final Place place,
            final Census1851Head head,
            final List<Census1851Resident> residents,
            final UniqueEventMerger eventMerger) {
        super(id, recordSet, CENSUS_DATE, place);
        this.head = head;
        this.residents = residents;
        this.eventMerger = eventMerger;
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
                new SingletonPersonEventSet(this.deathBeforeCensus(id)), //FIXME tighter bounds
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
        private final String occupation;
        private final String disability;

        protected Census1851Entry(
                final PersonId id,
                final Name name,
                final Sex sex,
                final RelationshipFactory relationshipToHead,
                final MarriageConditionEventInference condition,
                final Age age,
                final Place birthPlace,
                final String occupation,
                final String disability) {
            this.id = id;
            this.name = name;
            this.sex = sex;
            this.relationshipToHead = relationshipToHead;
            this.condition = MoreObjects.firstNonNull(condition, MarriageConditionEventInference.NONE);
            this.age = age;
            this.birthPlace = birthPlace;
            this.occupation = occupation;
            this.disability = disability;
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
                    age,
                    birthPlace,
                    condition.inferEvents(id, record),
                    condition,
                    occupation,
                    disability,
                    record.eventMerger);
        }

    }

    public static class Census1851Head extends Census1851Entry {

        public Census1851Head(
                final PersonId id,
                final Name name,
                final Sex sex,
                final MarriageConditionEventInference condition,
                final Age age,
                final Place birthPlace,
                final String occupation,
                final String disability) {
            super(id, name, sex, (a, b) -> null, condition, age, birthPlace, occupation, disability);
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
                final Place birthPlace,
                final String occupation,
                final String disability) {
            super(id, name, sex, Objects.requireNonNull(relationshipToHead), condition, age, birthPlace, occupation, disability);
        }

    }

    public static class Census1851Person extends AbstractCensusPerson {

        private final Place residencePlace;
        private final Age age;
        private final Place birthPlace;
        private final Relationship relationshipToHead;
        private final Set<? extends PersonEvent> extraEvents;
        private final MarriageConditionEventInference condition;
        private final String disability;
        private final UniqueEventMerger eventMerger;

        Census1851Person(
                final PersonId id,
                final Name name,
                final Gender gender,
                final Place residencePlace,
                final Relationship relationshipToHead,
                final Age age,
                final Place birthPlace,
                final Set<? extends PersonEvent> extraEvents,
                final MarriageConditionEventInference condition,
                final String occupation,
                final String disability,
                final UniqueEventMerger eventMerger) {
            super(id, name, gender, CENSUS_DATE, occupation);
            this.residencePlace = Objects.requireNonNull(residencePlace, "residence place");
            this.age = age;
            this.birthPlace = birthPlace;
            this.relationshipToHead = relationshipToHead;
            this.extraEvents = extraEvents;
            this.condition = condition;
            this.disability = disability;
            this.eventMerger = eventMerger;
        }

        boolean isHead() {
            return relationshipToHead == null;
        }

        boolean isWidowed() {
            return condition != null && condition.isWidowed();
        }

        @Nonnull
        @Override
        public PersonEventSet events() {
            final var events = new MutablePersonEventSet(eventMerger, this.birth(age, birthPlace), this.residence(age, residencePlace));
            events.addAll(extraEvents);
            return events;
        }

        @Nonnull
        @Override
        public Set<? extends PersonFeature> features() {
            return isBlank(disability)
                    ? Collections.emptySet()
                    : Collections.singleton(new Disability(disability));
        }

    }

}
