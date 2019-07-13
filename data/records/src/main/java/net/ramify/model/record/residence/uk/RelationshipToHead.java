package net.ramify.model.record.residence.uk;

import net.ramify.model.person.PersonId;
import net.ramify.model.relationship.Relationship;

import javax.annotation.Nonnull;

public interface RelationshipToHead {

    @Nonnull
    Relationship relationship(PersonId head, PersonId personId);

}
