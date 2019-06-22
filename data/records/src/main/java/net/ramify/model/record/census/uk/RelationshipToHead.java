package net.ramify.model.record.census.uk;

import net.ramify.model.person.PersonId;
import net.ramify.model.relationship.Relationship;

import javax.annotation.Nonnull;

public interface RelationshipToHead {

    @Nonnull
    Relationship relationship(PersonId head, PersonId personId);

}
