package net.ramify.model.person.feature;

import net.ramify.model.person.features.PersonFeature;
import net.ramify.model.person.proto.PersonProto;

import javax.annotation.Nonnull;
import java.util.Objects;

abstract class AbstractPersonFeature implements PersonFeature {

    @Nonnull
    @Override
    public PersonProto.PersonFeature.Builder toProtoBuilder() {
        return PersonFeature.super.toProtoBuilder()
                .setName(this.name());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        return this.getClass() == obj.getClass()
                && Objects.equals(this.value(), ((PersonFeature) obj).value());
    }

    @Override
    public int hashCode() {
        return this.getClass().getName().hashCode() + this.value().hashCode();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "=" + this.value();
    }

}
