package net.ramify.model.record.uk.census;

import net.ramify.model.family.relationship.Relationship;
import net.ramify.model.person.AgeAndOccupationDetails;
import net.ramify.model.person.Person;
import net.ramify.model.person.age.AgeRange;
import net.ramify.model.place.address.Address;

import java.util.Collection;
import java.util.function.BiFunction;

public class Uk1851CensusRecord extends UkNamedCensusRecord<Uk1851CensusRecord.Uk1851CensusPerson> {

    private final Uk1851CensusHead head;

    public Uk1851CensusRecord(
            final Address address,
            final Uk1851CensusHead head,
            final Collection<Uk1851CensusPerson> others) {
        super(CENSUS_1851, address, head, others);
        this.head = head;
    }

    @Override
    protected Relationship relationToHead(final Uk1851CensusPerson person) {
        return person.relationshipTo(head);
    }

    public static abstract class Uk1851CensusPerson extends AgeAndOccupationDetails {

        private final BiFunction<Person, Person, Relationship> relationshipToHead;
        private final Address birthplace;

        public Uk1851CensusPerson(
                final Person person,
                final AgeRange age,
                final String occupation,
                final BiFunction<Person, Person, Relationship> relationshipToHead,
                final Address birthplace) {
            super(person, age, occupation, CENSUS_1851);
            this.relationshipToHead = relationshipToHead;
            this.birthplace = birthplace;
        }

        Relationship relationshipTo(final Uk1851CensusHead head) {
            return relationshipToHead.apply(this.person(), head.person());
        }

        @Override
        protected Address birthplace() {
            return birthplace;
        }

    }

    public static class Uk1851CensusHead extends Uk1851CensusPerson {

        public Uk1851CensusHead(
                final Person person,
                final AgeRange age,
                final String occupation,
                final Address birthplace) {
            super(person, age, occupation, null, birthplace);
        }

    }

}
