package net.ramify.model.person.gender;

import javax.annotation.Nonnull;

public enum Sex implements Gender {

    MALE("Male"),
    FEMALE("Female");

    private final String value;

    Sex(final String value) {
        this.value = value;
    }

    @Nonnull
    @Override
    public String value() {
        return value;
    }

    public Sex inverse() {
        return this == MALE ? FEMALE : MALE;
    }

}
