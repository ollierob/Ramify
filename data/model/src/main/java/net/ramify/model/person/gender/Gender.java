package net.ramify.model.person.gender;

public interface Gender {

    Gender MALE = Sex.MALE;
    Gender FEMALE = Sex.FEMALE;
    Gender UNKNOWN = null;

    default boolean equals(final Gender that) {
        return this == that || that == UNKNOWN;
    }

}
