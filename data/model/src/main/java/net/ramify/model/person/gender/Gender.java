package net.ramify.model.person.gender;

import javax.annotation.Nonnull;

public interface Gender {

    Sex MALE = Sex.MALE;
    Sex FEMALE = Sex.FEMALE;
    Gender UNKNOWN = () -> "Unknown";

    @Nonnull
    String value();

    default boolean equals(final Gender that) {
        return this == that || this == UNKNOWN || that == UNKNOWN;
    }

    default Gender inverse() {
        return this instanceof Sex ? ((Sex) this).inverse() : this;
    }

}
