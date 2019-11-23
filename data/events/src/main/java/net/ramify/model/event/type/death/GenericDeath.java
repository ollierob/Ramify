package net.ramify.model.event.type.death;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.AbstractEvent;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.event.type.Death;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.age.Age;
import net.ramify.model.place.Place;

import javax.annotation.Nonnull;

public class GenericDeath extends AbstractEvent<GenericDeath> implements Death {

    public GenericDeath(final PersonId personId, final DateRange date) {
        super(personId, date);
    }

    @Nonnull
    @Override
    public EventProto.Event.Builder toProtoBuilder() {
        return Death.super.toProtoBuilder();
    }

    public Death with(final Age age) {
        return age == null ? this : new DeathWithAge(this, age);
    }

    public Death with(final Place place) {
        return place == null ? this : new DeathWithPlace(this, place);
    }

}
