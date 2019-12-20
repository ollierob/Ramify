package net.ramify.model.person;

import net.ramify.data.proto.BuildsProto;
import net.ramify.model.event.collection.HasPersonEvents;
import net.ramify.model.person.gender.HasGender;
import net.ramify.model.person.name.HasName;
import net.ramify.model.person.proto.PersonProto;

import javax.annotation.Nonnull;

public interface Person extends HasPerson, HasName, HasGender, HasPersonEvents, BuildsProto<PersonProto.Person> {

    @Override
    PersonId personId();

    @Override
    @Deprecated
    default Person person() {
        return this;
    }

    @Nonnull
    default PersonProto.Person.Builder toProtoBuilder() {
        final var builder = PersonProto.Person.newBuilder()
                .setId(this.personId().value())
                .setGender(this.gender().value());
        //.addAllEvents(Iterables.transform(this.sortedEvents(), Event::toProto));
        final var events = this.events();
        for (final var event : events.sortedEvents()) {
            final var eventBuilder = event.toProtoBuilder();
            events.inferAge(event).ifPresent(age -> eventBuilder.setComputedAge(age.toProto()));
            builder.addEvents(eventBuilder);
        }
        final var name = this.name();
        if (!name.isUnknown()) builder.setName(name.toProto());
        return builder;
    }

    @Nonnull
    @Override
    default PersonProto.Person toProto() {
        return this.toProtoBuilder().build();
    }

}
