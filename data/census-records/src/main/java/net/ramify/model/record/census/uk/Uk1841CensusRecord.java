package net.ramify.model.record.census.uk;

import net.ramify.model.family.relationship.Relationship;
import net.ramify.model.family.relationship.UnknownRelationship;
import net.ramify.model.person.AgeAndOccupationDetails;
import net.ramify.model.person.Person;
import net.ramify.model.person.age.AgeRange;
import net.ramify.model.place.address.Address;

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
        return new UnknownRelationship(this.head().person(), other.person());
    }

    public static class Uk1841CensusRecordPerson extends AgeAndOccupationDetails {

        public Uk1841CensusRecordPerson(
                final Person person,
                final int age,
                final String occupation) {
            super(person, new Uk1841CensusRecordAgeRange(age), occupation);
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
