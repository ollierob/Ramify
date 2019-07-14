package net.ramify.model.event.type.marriage;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.AbstractEvent;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.event.type.LifeEvent;
import net.ramify.model.person.PersonId;

import javax.annotation.Nonnull;

public class GenericMarriage extends AbstractEvent<GenericMarriage> implements LifeEvent, Marriage {

    public GenericMarriage(final PersonId personId, final DateRange date) {
        super(personId, date);
    }

    @Nonnull
    @Override
    public String title() {
        return "Marriage";
    }

    @Nonnull
    @Override
    public EventProto.Event.Builder toProtoBuilder() {
        return Marriage.super.toProtoBuilder();
    }

}
