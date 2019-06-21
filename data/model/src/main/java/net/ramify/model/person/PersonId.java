package net.ramify.model.person;

public interface PersonId extends HasPerson {

    default PersonId personId() {
        return this;
    }

}
