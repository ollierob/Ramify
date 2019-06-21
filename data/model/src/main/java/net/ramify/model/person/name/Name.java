package net.ramify.model.person.name;

import java.util.List;

public interface Name {

    static Name of(final String forename, final String surname) {
        return new ForenameSurname(forename, List.of(), surname);
    }

    Name UNKNOWN = () -> "?";

    default boolean isUnknown() {
        return this == UNKNOWN;
    }

    String value();

}
