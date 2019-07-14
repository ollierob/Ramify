package net.ramify.model.event.type.birth;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.AbstractEvent;
import net.ramify.model.event.proto.EventProto;
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

    @Nonnull
    @Override
    public EventProto.Event.Builder toProtoBuilder() {
        return Birth.super.toProtoBuilder();
    }

}
