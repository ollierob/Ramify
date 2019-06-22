package net.ramify.model.record.census.uk;

import com.google.common.collect.Sets;
import net.ramify.model.date.DateRange;
import net.ramify.model.date.ExactDate;
import net.ramify.model.event.Event;
import net.ramify.model.event.type.birth.GenericBirth;
import net.ramify.model.event.type.residence.GenericResidence;
import net.ramify.model.family.Family;
import net.ramify.model.occupation.Occupation;
import net.ramify.model.person.AbstractPerson;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.gender.Sex;
import net.ramify.model.person.name.Name;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.census.CensusRecord;
import net.ramify.utils.collections.SetUtils;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Set;

public class Register1939Record extends CensusRecord {

    public static final ExactDate CENSUS_DATE = DateRange.on(LocalDate.of(1939, Month.SEPTEMBER, 29));

    private final List<Register1939Entry> entries;

    public Register1939Record(final RecordId id, final PlaceId placeId, final List<Register1939Entry> entries) {
        super(id, CENSUS_DATE, placeId);
        this.entries = entries;
    }

    @Nonnull
    @Override
    public Set<Register1939Person> people() {
        return SetUtils.transform(entries, entry -> entry.build(this));
    }

    @Nonnull
    @Override
    public Set<? extends Family> families() {
        throw new UnsupportedOperationException(); //TODO
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
            return new Register1939Person(id, name, gender, record.placeId(), DateRange.on(birthDate), occupation);
        }

    }

    public static class Register1939Person extends AbstractPerson {

        private final PlaceId residence;
        private final ExactDate birthDate;
        private final Occupation occupation;

        Register1939Person(
                final PersonId id,
                final Name name,
                final Sex gender,
                final PlaceId residence,
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
                    new GenericBirth(this.personId(), birthDate),
                    new GenericResidence(this.personId(), CENSUS_DATE, residence));
        }

    }

}
