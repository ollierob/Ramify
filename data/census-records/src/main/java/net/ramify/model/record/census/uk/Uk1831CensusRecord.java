package net.ramify.model.record.census.uk;

import net.ramify.model.person.PersonalDetails;
import net.ramify.model.person.age.AgeRange;
import net.ramify.model.place.address.Address;

import java.util.Map;

public class Uk1831CensusRecord extends UkEnumeratedCensusRecord {

    protected Uk1831CensusRecord(
            final Address address,
            final PersonalDetails head,
            final Map<AgeRange, Integer> maleCount,
            final Map<AgeRange, Integer> femaleCount) {
        super(CENSUS_1831, address, head, maleCount, femaleCount);
    }

}
