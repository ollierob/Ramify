package net.ramify.model.event.type;

import net.ramify.model.event.proto.EventProto;
import net.ramify.model.person.age.Age;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface BirthEvent extends UniqueEvent {

    @Nonnull
    @Override
    default Optional<Age> givenAge() {
        return Optional.of(Age.ZERO);
    }

    @Nonnull
    @Override
    default Optional<String> occupation() {
        return Optional.empty();
    }

    @Override
    default <R> R handleWith(final EventHandler<R> handler) {
        return handler.handle(this);
    }

    @Nonnull
    @Override
    default String title() {
        return "Birth";
    }

    @Nonnull
    @Override
    default Class<? extends UniqueEvent> uniqueType() {
        return BirthEvent.class;
    }

    @Nonnull
    @Override
    default EventProto.Event.Builder toProtoBuilder() {
        return UniqueEvent.super.toProtoBuilder()
                .setType(EventProto.RecordType.BIRTH);
    }

}
