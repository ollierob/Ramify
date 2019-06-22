package net.ramify.model.person.name;

import net.ramify.data.proto.BuildsProto;
import net.ramify.model.person.proto.PersonProto;

import javax.annotation.Nonnull;

public interface Name extends BuildsProto<PersonProto.Name> {

    @Nonnull
    PersonProto.Name.Builder toProtoBuilder();

    @Nonnull
    @Override
    default PersonProto.Name toProto() {
        return this.toProtoBuilder().build();
    }

}
