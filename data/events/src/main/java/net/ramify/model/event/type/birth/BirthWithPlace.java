package net.ramify.model.event.type.birth;

import net.ramify.model.event.proto.EventProto;
import net.ramify.model.event.type.BirthEvent;
import net.ramify.model.event.type.misc.EventWithPlace;
import net.ramify.model.person.PersonId;
import net.ramify.model.place.HasPlace;
import net.ramify.model.place.Place;

import javax.annotation.Nonnull;

public class BirthWithPlace extends EventWithPlace<BirthEvent> implements BirthEvent, HasPlace {

    public BirthWithPlace(final BirthEvent event, final Place birthPlace) {
        super(event, birthPlace);
    }

    @Nonnull
    @Override
    public PersonId personId() {
        throw new UnsupportedOperationException(); //TODO
    }

    @Nonnull
    @Override
    public EventProto.Event.Builder toProtoBuilder() {
        return BirthEvent.super.toProtoBuilder();
    }

}
