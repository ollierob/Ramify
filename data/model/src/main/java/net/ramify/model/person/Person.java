package net.ramify.model.person;

import net.ramify.model.person.gender.Gender;
import net.ramify.model.person.name.Name;

import javax.annotation.Nonnull;

public interface Person extends HasPerson {

    @Nonnull
    Name name();

    @Nonnull
    Gender gender();

    @Nonnull
    @Deprecated
    @Override
    default Person person() {
        return this;
    }

    static Person unknown(final Gender gender) {
        return named(Name.UNKNOWN, gender);
    }

    static Person named(final Name name, final Gender gender) {
        throw new UnsupportedOperationException(); //TODO
    }

}
