package net.ramify.model.record.census.uk;

import net.ramify.model.event.DateRange;
import net.ramify.model.family.relationship.Relationship;
import net.ramify.model.family.relationship.UnknownRelationship;
import net.ramify.model.person.AgeAndOccupationDetails;
import net.ramify.model.place.address.Address;

import java.util.Collection;

public class Uk1841CensusRecord extends UkNamedCensusRecord<AgeAndOccupationDetails> {

    public Uk1841CensusRecord(
            final DateRange date,
            final Address address,
            final AgeAndOccupationDetails head,
            final Collection<AgeAndOccupationDetails> others) {
        super(date, address, head, others);
    }

    @Override
    protected Relationship relationshipBetween(final AgeAndOccupationDetails head, final AgeAndOccupationDetails other) {
        return new UnknownRelationship(head.person(), other.person());
    }
}
