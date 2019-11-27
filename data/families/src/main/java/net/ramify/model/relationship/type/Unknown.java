package net.ramify.model.relationship.type;

import net.ramify.model.person.HasPersonId;
import net.ramify.model.relationship.AbstractRelationship;

import javax.annotation.Nonnull;

public class Unknown extends AbstractRelationship implements UnknownRelationship {

    public Unknown(final HasPersonId from, final HasPersonId to) {
        super(from, to);
    }

    @Nonnull
    @Override
    public String describeTo() {
        return this.describeFrom();
    }

}
