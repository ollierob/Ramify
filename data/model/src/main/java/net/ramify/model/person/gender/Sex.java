package net.ramify.model.person.gender;

public enum Sex implements Gender {

    MALE,
    FEMALE;

    @Override
    public boolean is(final Gender that) {
        return that == UNKNOWN || this == that;
    }

    public Sex inverse() {
        return this == MALE ? FEMALE : MALE;
    }

}
