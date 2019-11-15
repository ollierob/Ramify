package net.ramify.model.relationship;

import net.ramify.model.person.HasPersonId;
import net.ramify.model.person.PersonId;
import net.ramify.model.relationship.type.UnknownRelationship;

import javax.annotation.Nonnull;

public interface RelationshipFactory {

    @Nonnull
    Relationship relationshipBetween(HasPersonId from, HasPersonId to);

    RelationshipFactory UNKNOWN = (from, to) -> new UnknownRelationship() {

        @Nonnull
        @Override
        public PersonId fromId() {
            return from.personId();
        }

        @Nonnull
        @Override
        public PersonId toId() {
            return to.personId();
        }

    };

}
