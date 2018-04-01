package net.ramify.model.person.gender;

public interface Gender {

    Gender MALE = Sex.MALE;

    Gender UNKNOWN = that -> true;
    Gender FEMALE = Sex.FEMALE;

    boolean is(Gender that);

}
