package net.ramify.model.person.name;

import net.ramify.data.proto.BuildsProto;
import net.ramify.model.person.proto.PersonProto;

import javax.annotation.Nonnull;

public interface Name extends BuildsProto<PersonProto.Name> {

    @Nonnull
    String value();

    default boolean contains(final String name) {
        return this.value().toLowerCase().contains(name.toLowerCase());
    }

    default boolean isUnknown() {
        if (this == UNKNOWN) return true;
        final var value = this.value();
        return value.isBlank() || value.equals(UNKNOWN.value());
    }

    @Nonnull
    default PersonProto.Name.Builder toProtoBuilder() {
        return PersonProto.Name.newBuilder();
    }

    @Nonnull
    @Override
    default PersonProto.Name toProto() {
        return this.toProtoBuilder().build();
    }

    Name UNKNOWN = () -> "?";

}
