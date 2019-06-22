package net.ramify.model.relationship.type;

import net.ramify.model.person.PersonId;
import net.ramify.model.relationship.AbstractRelationship;

import javax.annotation.Nonnull;

public class ChildParent extends AbstractRelationship implements CosanguinealRelationship {

    public ChildParent(final PersonId child, final PersonId parent) {
        super(child, parent);
    }

    public PersonId child() {
        return this.fromId();
    }

    public PersonId parent() {
        return this.toId();
    }

    @Nonnull
    @Override
    public ParentChild inverse() {
        return new ParentChild(this.parent(), this.child());
    }
}
