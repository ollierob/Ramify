package net.ramify.model.event.type.civil;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.type.AbstractEvent;
import net.ramify.model.event.type.Birth;
import net.ramify.model.person.PersonId;

import javax.annotation.Nonnull;

public class GenericBirth extends AbstractEvent<GenericBirth> implements Birth {

    private final PersonId personId;

    public GenericBirth(final PersonId personId, final DateRange date) {
        super(personId, date);
        this.personId = personId;
    }

    @Nonnull
    @Override
    public PersonId personId() {
        return personId;
    }

}
