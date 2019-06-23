package net.ramify.model.relationship;

import net.ramify.model.person.HasPersonId;

import javax.annotation.Nonnull;

public interface RelationshipFactory {

    @Nonnull
    Relationship relationshipBetween(HasPersonId from, HasPersonId to);

}
