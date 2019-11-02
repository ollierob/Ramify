package net.ramify.model.event.type.death;

import net.ramify.model.event.proto.EventProto;
import net.ramify.model.event.type.Death;
import net.ramify.model.event.type.misc.ComposedEvent;
import net.ramify.model.person.age.Age;

import javax.annotation.Nonnull;

class DeathWithAge extends ComposedEvent<Death> implements Death {

    private final Age age;

    DeathWithAge(final Death delegate, final Age age) {
        super(delegate);
        this.age = age;
    }

    @Nonnull
    @Override
    public EventProto.Event.Builder toProtoBuilder() {
        return super.toProtoBuilder().setGivenAge(age.lowerBound().getYears());
    }

}
