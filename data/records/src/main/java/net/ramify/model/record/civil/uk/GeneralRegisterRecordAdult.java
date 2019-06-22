package net.ramify.model.record.civil.uk;

import net.ramify.model.event.type.residence.GenericResidence;
import net.ramify.model.occupation.Occupation;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.gender.Sex;
import net.ramify.model.person.name.Name;
import net.ramify.model.place.PlaceId;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

public class GeneralRegisterRecordAdult {

    private final PersonId id;
    private final Name name;
    @Nonnull
    private final Sex gender;
    private final Occupation occupation;
    @CheckForNull
    private final PlaceId residence;

    public GeneralRegisterRecordAdult(
            @Nonnull final PersonId id,
            @Nonnull final Name name,
            @Nonnull final Sex gender,
            @CheckForNull final Occupation occupation,
            @CheckForNull final PlaceId residence) {
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

    @Nonnull
    Person buildWithResidence(final GeneralRegisterRecord record) {
        return new GeneralRegisterRecordPerson(
                id,
                name,
                gender,
                residence == null ? null : new GenericResidence(id, record.date(), residence)); //TODO infer birth
    }

}
