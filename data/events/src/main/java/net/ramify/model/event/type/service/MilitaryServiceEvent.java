package net.ramify.model.event.type.service;

import net.ramify.model.event.AbstractPersonEvent;
import net.ramify.model.event.EventId;
import net.ramify.model.event.EventProperties;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.event.type.LifeEvent;
import net.ramify.model.person.PersonId;
import net.ramify.model.place.Place;

import javax.annotation.Nonnull;

public class MilitaryServiceEvent
        extends AbstractPersonEvent<MilitaryServiceEvent>
        implements ServiceEvent {

    public MilitaryServiceEvent(final EventId id, final PersonId personId, final EventProperties properties) {
        super(id, personId, properties);
    }

    @Nonnull
    @Override
    public LifeEvent with(final Place place) {
        if (place == null) return this;
        throw new UnsupportedOperationException();
    }

    @Nonnull
    @Override
    public String title() {
        return "Military";
    }

    @Nonnull
    @Override
    public EventProto.Event.Builder toProtoBuilder() {
        return super.toProtoBuilder()
                .setType(EventProto.RecordType.MILITARY);
    }

}
