package net.ramify.model.person.gender;

public interface Gender {

    Gender UNKNOWN = that -> true;

    boolean is(Gender that);

}
