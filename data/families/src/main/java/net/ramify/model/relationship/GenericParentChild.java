package net.ramify.model.relationship;

import net.ramify.model.person.PersonId;

public class GenericParentChild extends AbstractRelationship {

    public GenericParentChild(final PersonId parent, final PersonId child) {
        super(parent, child);
    }

    public PersonId parent() {
        return this.from();
    }

}
