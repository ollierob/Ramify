package net.ramify.model.record.civil.uk;

import net.ramify.model.event.type.civil.GenericBirth;
import net.ramify.model.event.type.residence.GenericResidence;
import net.ramify.model.occupation.Occupation;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.gender.Sex;
import net.ramify.model.person.name.Name;
import net.ramify.model.place.PlaceId;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

public class GeneralRegisterRecordEntry {

    final PersonId id;
    final Name name;
    final Sex gender;
    final Occupation occupation;
    final PlaceId residence;

    public GeneralRegisterRecordEntry(
            @Nonnull final PersonId id,
            @Nonnull final Name name,
            @Nonnull final Sex gender,
            @CheckForNull final PlaceId residence,
            @CheckForNull final Occupation occupation) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.occupation = occupation;
        this.residence = residence;
    }

    @CheckForNull
    PlaceId residence() {
        return residence;
    }

    Person buildWithBirth(final GeneralRegisterBirth record) {
        return new GeneralRegisterRecordPerson(
                id,
                name,
                gender,
                new GenericBirth(id, record.birthDate()).with(record.birthPlace()));
    }

    Person buildWithResidence(final GeneralRegisterRecord record) {
        return new GeneralRegisterRecordPerson(
                id,
                name,
                gender,
                residence == null ? null : new GenericResidence(id, record.date(), residence)); //TODO infer birth
    }

}
