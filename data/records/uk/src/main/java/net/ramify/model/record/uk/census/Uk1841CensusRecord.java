package net.ramify.model.record.uk.census;

import net.ramify.model.family.relationship.Relationship;
import net.ramify.model.family.relationship.UnknownRelationship;
import net.ramify.model.person.NameAgeGenderOccupation;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.age.AgeRange;
import net.ramify.model.person.gender.Gender;
import net.ramify.model.person.name.ForenameSurname;
import net.ramify.model.place.address.Address;
import net.ramify.model.place.address.CountyInCountry;

import javax.annotation.Nonnull;
import java.time.Period;
import java.util.Collection;

public class Uk1841CensusRecord extends UkNamedCensusRecord<Uk1841CensusRecord.Uk1841CensusRecordPerson> {

    public Uk1841CensusRecord(
            final Address address,
            final Uk1841CensusRecordPerson head,
            final Collection<Uk1841CensusRecordPerson> others) {
        super(CENSUS_1841, address, head, others);
    }

    @Override
    protected Relationship relationToHead(Uk1841CensusRecordPerson other) {
        return new UnknownRelationship(this.head().personId(), other.personId());
    }

    public static class Uk1841CensusRecordPerson extends NameAgeGenderOccupation {

        private final CountyInCountry birthplace;

        public Uk1841CensusRecordPerson(
                final PersonId person,
                final ForenameSurname name,
                final Gender gender,
                final int age,
                final String occupation,
                final CountyInCountry birthplace) {
            super(person, name, gender, new Uk1841CensusRecordAgeRange(age), CENSUS_1841, occupation);
            this.birthplace = birthplace;
        }

        @Override
        protected Address birthplace() {
            return birthplace;
        }

    }

    public static class Uk1841CensusRecordAgeRange implements AgeRange {

        private final int censusAge;

        Uk1841CensusRecordAgeRange(final int censusAge) {
            this.censusAge = censusAge;
        }

        @Nonnull
        @Override
        public Period min() {
            return Period.ofYears(censusAge);
        }

        @Nonnull
        @Override
        public Period max() {
            return this.rounded() ? Period.ofYears(censusAge + 4) : Period.ofYears(censusAge);
        }

        private boolean rounded() {
            return censusAge >= 15 && censusAge % 5 == 0;
        }

    }

}
