package net.ramify.model.record.residence.uk;

import net.ramify.model.date.BeforeDate;
import net.ramify.model.date.DateRange;
import net.ramify.model.date.ExactDate;
import net.ramify.model.event.EventId;
import net.ramify.model.event.collection.MutablePersonEventSet;
import net.ramify.model.event.collection.PersonEventSet;
import net.ramify.model.event.merge.UniqueEventMerger;
import net.ramify.model.family.Family;
import net.ramify.model.family.FamilyOfUnknownRelationships;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.age.Age;
import net.ramify.model.person.features.PersonFeature;
import net.ramify.model.person.gender.Gender;
import net.ramify.model.person.name.Name;
import net.ramify.model.place.Place;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.residence.CensusRecord;
import net.ramify.utils.collections.SetUtils;

import javax.annotation.Nonnull;
import java.time.Month;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Register1939Record extends CensusRecord {

    public static final ExactDate CENSUS_DATE = ExactDate.on(1939, Month.SEPTEMBER, 29);

    private final List<Register1939Entry> entries;
    private final UniqueEventMerger eventMerger;

    public Register1939Record(
            final RecordId id,
            final RecordSet recordSet,
            final Place place,
            final List<Register1939Entry> entries,
            final UniqueEventMerger eventMerger) {
        super(id, recordSet, CENSUS_DATE, place);
        this.entries = entries;
        this.eventMerger = eventMerger;
    }

    @Nonnull
    @Override
    public Set<Register1939Person> people() {
        return SetUtils.transform(entries, entry -> entry.build(this));
    }

    @Nonnull
    @Override
    public Family family() {
        return new FamilyOfUnknownRelationships(this.people());
    }

    public record Register1939Entry(
            PersonId id,
            Name name,
            Gender gender,
            DateRange birthDate,
            String occupation,
            EventId birthEventId,
            EventId residenceEventId) {

        public Register1939Entry(final PersonId id) {
            this(id, Name.UNKNOWN, Gender.UNKNOWN, BeforeDate.strictlyBefore(CENSUS_DATE), null, null, null);
        }

        Register1939Person build(final Register1939Record record) {
            return new Register1939Person(
                    id,
                    name,
                    gender,
                    birthEventId,
                    residenceEventId,
                    record.place(),
                    birthDate,
                    occupation,
                    record.eventMerger);
        }

    }

    public static class Register1939Person extends AbstractCensusPerson {

        private final Place residence;
        private final DateRange birthDate;
        private final EventId birthEventId, residenceEventId; //TODO use these
        private final UniqueEventMerger eventMerger;

        Register1939Person(
                final PersonId id,
                final Name name,
                final Gender gender,
                final EventId birthEventId,
                final EventId residenceEventId,
                final Place residence,
                final DateRange birthDate,
                final String occupation,
                final UniqueEventMerger eventMerger) {
            super(id, name, gender, CENSUS_DATE, occupation);
            this.birthEventId = birthEventId;
            this.residenceEventId = residenceEventId;
            this.residence = residence;
            this.birthDate = birthDate;
            this.eventMerger = eventMerger;
        }

        @Nonnull
        @Override
        public PersonEventSet events() {
            return new MutablePersonEventSet(
                    eventMerger,
                    this.birth(birthDate, null),
                    this.residence(Age.fromDates(birthDate, CENSUS_DATE), residence));
        }

        @Nonnull
        @Override
        public Set<? extends PersonFeature> features() {
            return Collections.emptySet();
        }

    }

}
