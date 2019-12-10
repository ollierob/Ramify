package net.ramify.model.record.residence.uk;

import com.google.common.collect.Sets;
import net.ramify.model.date.ExactDate;
import net.ramify.model.event.Event;
import net.ramify.model.event.type.birth.GenericBirthEvent;
import net.ramify.model.event.type.residence.GenericResidenceEvent;
import net.ramify.model.family.Family;
import net.ramify.model.family.FamilyOfUnknownRelationships;
import net.ramify.model.occupation.Occupation;
import net.ramify.model.person.AbstractPerson;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.gender.Sex;
import net.ramify.model.person.name.Name;
import net.ramify.model.place.Place;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.residence.CensusRecord;
import net.ramify.utils.collections.SetUtils;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Set;

public class Register1939Record extends CensusRecord {

    public static final ExactDate CENSUS_DATE = ExactDate.on(1939, Month.SEPTEMBER, 29);

    private final List<Register1939Entry> entries;

    public Register1939Record(
            final RecordId id,
            final RecordSet recordSet,
            final Place place,
            final List<Register1939Entry> entries) {
        super(id, recordSet, CENSUS_DATE, place);
        this.entries = entries;
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

    public static class Register1939Entry {

        private final PersonId id;
        private final Name name;
        private final Sex gender;
        private final LocalDate birthDate;
        private final Occupation occupation;

        public Register1939Entry(
                final PersonId id,
                final Name name,
                final Sex sex,
                final LocalDate birthDate,
                final Occupation occupation) {
            this.id = id;
            this.name = name;
            this.gender = sex;
            this.birthDate = birthDate;
            this.occupation = occupation;
        }

        Register1939Person build(final Register1939Record record) {
            return new Register1939Person(id, name, gender, record.place(), ExactDate.on(birthDate), occupation);
        }

    }

    public static class Register1939Person extends AbstractPerson {

        private final Place residence;
        private final ExactDate birthDate;
        private final Occupation occupation;

        Register1939Person(
                final PersonId id,
                final Name name,
                final Sex gender,
                final Place residence,
                final ExactDate birthDate,
                final Occupation occupation) {
            super(id, name, gender);
            this.residence = residence;
            this.birthDate = birthDate;
            this.occupation = occupation;
        }

        @Nonnull
        @Override
        public Set<? extends Event> events() {
            return Sets.newHashSet(
                    new GenericBirthEvent(this.personId(), birthDate),
                    new GenericResidenceEvent(this.personId(), CENSUS_DATE, residence));
        }

    }

}
