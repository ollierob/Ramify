package net.ramify.model.relationship.type;

import net.ramify.model.person.PersonId;
import net.ramify.model.relationship.AbstractRelationship;

public class ParentChild extends AbstractRelationship implements CosanguinealRelationship {

    public ParentChild(final PersonId parent, final PersonId child) {
        super(parent, child);
    }

    public PersonId parent() {
        return this.from();
    }

    public PersonId child() {
        return this.to();
    }

    public ChildParent inverse() {
        return new ChildParent(this.child(), this.parent());
    }

}
