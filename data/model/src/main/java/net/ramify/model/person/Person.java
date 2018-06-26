package net.ramify.model.person;

public interface Person extends HasPerson {

    default Person person() {
        return this;
    }

}
