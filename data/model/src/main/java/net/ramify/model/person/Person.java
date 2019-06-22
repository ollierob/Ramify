package net.ramify.model.person;

import com.google.common.collect.Iterables;
import net.ramify.data.proto.BuildsProto;
import net.ramify.model.event.Event;
import net.ramify.model.event.collection.HasPersonEvents;
import net.ramify.model.person.gender.HasGender;
import net.ramify.model.person.name.HasName;
import net.ramify.model.person.proto.PersonProto;

import javax.annotation.Nonnull;

public interface Person extends HasName, HasGender, HasPersonEvents, BuildsProto<PersonProto.Person> {

    @Nonnull
    default PersonProto.Person.Builder toProtoBuilder() {
        return PersonProto.Person.newBuilder()
                .setId(this.personId().value())
                .setName(this.name().toProto())
                .setGender(this.gender().value())
                .addAllEvents(Iterables.transform(this.events(), Event::toProto));
    }

    @Nonnull
    @Override
    default PersonProto.Person toProto() {
        return this.toProtoBuilder().build();
    }

}
