package net.ramify.model.relationship.type;

import net.ramify.model.person.PersonId;
import net.ramify.model.relationship.AbstractRelationship;

public class Married extends AbstractRelationship implements AffineRelationship {

    public Married(final PersonId from, final PersonId to) {
        super(from, to);
    }

    @Override
    public Married inverse() {
        return new Married(this.to(), this.from());
    }

}
