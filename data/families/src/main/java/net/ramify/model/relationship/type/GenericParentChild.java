package net.ramify.model.relationship.type;

import net.ramify.model.person.PersonId;
import net.ramify.model.relationship.AbstractRelationship;

public class GenericParentChild extends AbstractRelationship implements CosanguinealRelationship {

    public GenericParentChild(final PersonId parent, final PersonId child) {
        super(parent, child);
    }

    public PersonId parent() {
        return this.from();
    }

    public PersonId child() {
        return this.to();
    }

}
