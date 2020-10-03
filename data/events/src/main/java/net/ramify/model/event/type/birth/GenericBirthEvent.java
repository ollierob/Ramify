package net.ramify.model.event.type.birth;

import net.ramify.model.event.AbstractPersonEvent;
import net.ramify.model.event.EventId;
import net.ramify.model.event.EventProperties;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.event.type.BirthEvent;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.age.Age;
import net.ramify.model.place.Place;

import javax.annotation.Nonnull;
import java.util.Optional;

public class GenericBirthEvent extends AbstractPersonEvent<GenericBirthEvent> implements BirthEvent {

    public GenericBirthEvent(final EventId id, final PersonId personId, final EventProperties properties) {
        super(id, personId, properties);
    }

    @Override
    public BirthEvent with(final Place place) {
        return place == null ? this : new BirthWithPlace(this, place);
    }

    @Nonnull
    @Override
    public Optional<Age> givenAge() {
        return BirthEvent.super.givenAge();
    }

    @Nonnull
    @Override
    public Optional<Age> computedAge() {
        return BirthEvent.super.computedAge();
    }

    @Override
    public Optional<String> occupation() {
        return BirthEvent.super.occupation();
    }

    @Nonnull
    @Override
    public EventProto.Event.Builder toProtoBuilder() {
        return BirthEvent.super.toProtoBuilder();
    }

}
