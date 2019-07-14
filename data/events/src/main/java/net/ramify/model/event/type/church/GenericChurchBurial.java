package net.ramify.model.event.type.church;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.AbstractEvent;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.event.type.Burial;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.age.Age;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.time.Period;

public class GenericChurchBurial extends AbstractEvent<GenericChurchBurial> implements Burial {

    private final Age age;

    public GenericChurchBurial(final PersonId personId, final DateRange date, @CheckForNull final Age age) {
        super(personId, date);
        this.age = age;
    }

    @Nonnull
    @Override
    public EventProto.Event.Builder toProtoBuilder() {
        return Burial.super.toProtoBuilder()
                .setGivenAge(age.exact().map(Period::getYears).orElse(0));
    }
}
