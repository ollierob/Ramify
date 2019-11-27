package net.ramify.model.relationship.type;

import net.ramify.model.person.HasPersonId;
import net.ramify.model.relationship.AbstractRelationship;

import javax.annotation.Nonnull;

public class GodParentChild extends AbstractRelationship implements FictiveRelationship {

    public GodParentChild(final HasPersonId godParent, final HasPersonId child) {
        super(godParent, child);
    }

    @Override
    public boolean isDirected() {
        return true;
    }

    @Nonnull
    @Override
    public String describeFrom() {
        return "God-parent";
    }

    @Nonnull
    @Override
    public String describeTo() {
        return "God-child";
    }

}
