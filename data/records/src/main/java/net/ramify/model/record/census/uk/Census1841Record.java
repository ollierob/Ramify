package net.ramify.model.record.census.uk;

import com.google.common.collect.Sets;
import net.ramify.model.date.DateRange;
import net.ramify.model.date.ExactDate;
import net.ramify.model.event.Event;
import net.ramify.model.event.type.civil.GenericBirth;
import net.ramify.model.event.type.residence.GenericResidence;
import net.ramify.model.family.Family;
import net.ramify.model.family.FamilyOfUnknownRelationships;
import net.ramify.model.person.AbstractPerson;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.age.Age;
import net.ramify.model.person.gender.Sex;
import net.ramify.model.person.name.Name;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.census.CensusRecord;
import net.ramify.utils.collections.SetUtils;

import javax.annotation.Nonnull;
import java.time.Month;
import java.time.Period;
import java.util.List;
import java.util.Set;

public class Census1841Record extends CensusRecord {

    public static final ExactDate CENSUS_DATE = ExactDate.on(1841, Month.JUNE, 6);

    private final List<Census1841Entry> entries;

    public Census1841Record(final RecordId id, final PlaceId placeId, final List<Census1841Entry> entries) {
        super(id, CENSUS_DATE, placeId);
        this.entries = entries;
    }

    @Nonnull
    @Override
    public Set<Census1841Person> people() {
        return SetUtils.transform(entries, entry -> entry.build(this));
    }

    @Nonnull
    @Override
    public Family family() {
        return new FamilyOfUnknownRelationships(this.people());
    }

    DateRange inferBirthDate(final Period age) {
        return inferAge(age).birthDate(CENSUS_DATE);
    }

    static Age inferAge(final Period age) {
        if (age.getMonths() > 0 || age.getDays() > 0 || age.getYears() < 15 || age.getYears() % 5 != 0) return Age.exactly(age);
        final var years = age.getYears();
        return Age.between(years, years + 4);
    }

    public static class Census1841Entry {

        private final PersonId id;
        private final Name name;
        private final Period age;
        private final Sex sex;
        private final PlaceId birthPlace;

        public Census1841Entry(final PersonId id, final Name name, final Period age, final Sex sex, final PlaceId birthPlace) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.sex = sex;
            this.birthPlace = birthPlace;
        }

        Census1841Person build(final Census1841Record record) {
            return new Census1841Person(id, name, sex, record.placeId(), record.inferBirthDate(age), birthPlace);
        }

    }

    public static class Census1841Person extends AbstractPerson {

        private final PlaceId residencePlace;
        private final PlaceId birthPlace;
        private final DateRange birthDate;

        Census1841Person(
                final PersonId id,
                final Name name,
                final Sex sex,
                final PlaceId residencePlace,
                final DateRange birthDate,
                final PlaceId birthPlace) {
            super(id, name, sex);
            this.residencePlace = residencePlace;
            this.birthPlace = birthPlace;
            this.birthDate = birthDate;
        }

        @Nonnull
        @Override
        public Set<? extends Event> events() {
            return Sets.newHashSet(
                    new GenericBirth(this.personId(), birthDate).with(birthPlace),
                    new GenericResidence(this.personId(), CENSUS_DATE, residencePlace));
        }

    }

}
