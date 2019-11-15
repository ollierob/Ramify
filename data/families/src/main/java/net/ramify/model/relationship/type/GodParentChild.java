package net.ramify.model.relationship.type;

import net.ramify.model.person.HasPersonId;
import net.ramify.model.relationship.AbstractRelationship;

public class GodParentChild extends AbstractRelationship implements FictiveRelationship {

    public GodParentChild(final HasPersonId from, final HasPersonId to) {
        super(from, to);
    }

    @Override
    public boolean isDirected() {
        return true;
    }

}
