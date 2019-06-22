package net.ramify.model.relationship.type;

import net.ramify.model.person.HasPersonId;
import net.ramify.model.relationship.AbstractRelationship;

public class Married extends AbstractRelationship implements AffineRelationship {

    public Married(final HasPersonId from, final HasPersonId to) {
        super(from, to);
    }

    @Override
    public Married inverse() {
        return new Married(this.toId(), this.fromId());
    }

}
