package net.ramify.model.person.name;

import net.ramify.data.proto.BuildsProto;
import net.ramify.model.person.proto.NameProto;
import net.ramify.utils.collections.SetUtils;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Set;

public interface Name extends BuildsProto<NameProto.Name> {

    @Nonnull
    String value();

    @Nonnull
    default Set<String> variations() {
        if (this.isUnknown()) return Collections.emptySet();
        return Collections.singleton(this.value());
    }

    default boolean contains(final String name) {
        return this.value().toLowerCase().contains(name.toLowerCase());
    }

    default boolean isUnknown() {
        if (this == UNKNOWN) return true;
        final var value = this.value();
        return value.isBlank() || value.equals(UNKNOWN.value()) || value.equalsIgnoreCase("unknown");
    }

    @Nonnull
    default NameProto.Name.Builder toProtoBuilder() {
        return NameProto.Name.newBuilder()
                .setValue(this.value())
                .setUnknown(this.isUnknown())
                .addAllVariation(SetUtils.without(this.variations(), this.value()));
    }

    @Nonnull
    @Override
    default NameProto.Name toProto() {
        return this.toProtoBuilder().build();
    }

    Name UNKNOWN = () -> "?";

}
