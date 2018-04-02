package net.ramify.model.person.gender;

public interface Gender {

    Gender FEMALE = Sex.FEMALE;
    Gender MALE = Sex.MALE;
    Gender UNKNOWN = that -> true;

    boolean is(Gender that);

}
