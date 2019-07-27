package net.ramify.model.person;

import net.ramify.model.Id;

import java.util.UUID;

public class PersonId extends Id implements HasPersonId {

    @Deprecated
    public static PersonId random() {
        return new PersonId(UUID.randomUUID().toString());
    }

    public PersonId(final String value) {
        super(value);
    }

    @Override
    public PersonId personId() {
        return this;
    }

}
