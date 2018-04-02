package net.ramify.model.person.name;

public interface Name {

    Name UNKNOWN = new Name() {

    };

    @Override
    String toString();

}
