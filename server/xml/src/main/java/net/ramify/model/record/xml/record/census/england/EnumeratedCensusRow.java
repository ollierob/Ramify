package net.ramify.model.record.xml.record.census.england;

import net.ramify.model.person.Person;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
interface EnumeratedCensusRow {

    @Nonnull
    Person person();

    @Nonnull
    CensusRelationshipToHead relationshipToHead();

    default boolean isHead() {
        return this.relationshipToHead().isHead();
    }

}
