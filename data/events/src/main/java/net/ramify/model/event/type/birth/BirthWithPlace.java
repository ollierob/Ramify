package net.ramify.model.event.type.birth;

import net.ramify.model.event.proto.EventProto;
import net.ramify.model.event.type.Birth;
import net.ramify.model.event.type.misc.EventWithPlace;
import net.ramify.model.place.HasPlace;
import net.ramify.model.place.Place;

import javax.annotation.Nonnull;

public class BirthWithPlace extends EventWithPlace<Birth> implements Birth, HasPlace {

    public BirthWithPlace(final Birth event, final Place birthPlace) {
        super(event, birthPlace);
    }

    @Nonnull
    @Override
    public EventProto.Event.Builder toProtoBuilder() {
        return Birth.super.toProtoBuilder();
    }

}
