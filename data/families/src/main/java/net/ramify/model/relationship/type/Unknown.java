package net.ramify.model.relationship.type;

import net.ramify.model.person.PersonId;
import net.ramify.model.relationship.AbstractRelationship;

public class Unknown extends AbstractRelationship implements UnknownRelationship {

    public Unknown(PersonId from, PersonId to) {
        super(from, to);
    }

}
