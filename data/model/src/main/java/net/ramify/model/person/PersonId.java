package net.ramify.model.person;

import net.ramify.model.Id;

public class PersonId extends Id implements HasPersonId {

    public PersonId(final String value) {
        super(value);
    }

    @Override
    public PersonId personId() {
        return this;
    }

}
