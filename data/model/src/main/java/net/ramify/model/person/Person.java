package net.ramify.model.person;

import com.google.common.collect.Iterables;
import net.ramify.data.proto.BuildsProto;
import net.ramify.model.event.collection.HasPersonEvents;
import net.ramify.model.person.features.HasPersonFeatures;
import net.ramify.model.person.features.PersonFeature;
import net.ramify.model.person.gender.HasGender;
import net.ramify.model.person.name.HasName;
import net.ramify.model.person.proto.PersonProto;

import javax.annotation.Nonnull;

public interface Person extends HasPerson, HasName, HasGender, HasPersonEvents, HasPersonFeatures, BuildsProto<PersonProto.Person> {

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
                .setGender(this.gender().value())
                .setName(this.name().toProto())
                .addAllFeature(Iterables.transform(this.features(), PersonFeature::toProto));
        //FIXME push this into a different proto
        final var events = this.events();
        for (final var event : events.sortedEvents()) {
            final var eventBuilder = event.toProtoBuilder();
            events.inferAge(event).ifPresent(age -> eventBuilder.setComputedAge(age.toProto()));
            builder.addEvents(eventBuilder);
        }
        return builder;
    }

    @Nonnull
    @Override
    default PersonProto.Person toProto() {
        return this.toProtoBuilder().build();
    }

}
