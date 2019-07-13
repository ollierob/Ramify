package net.ramify.model.person.name;

import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import net.ramify.model.person.proto.PersonProto;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public class ForenameSurname implements Name {

    private static final Joiner JOIN_SPACES = Joiner.on(' ');

    private final String prefix;
    private final List<String> forenames;
    private final String surname;
    private final String suffix;

    public ForenameSurname(final String forename, final String surname) {
        this(null, Collections.singletonList(forename), surname, null);
    }

    public ForenameSurname(final String prefix, final List<String> forenames, final String surname, final String suffix) {
        this.prefix = prefix;
        this.forenames = forenames;
        this.surname = surname;
        this.suffix = suffix;
    }

    @Nonnull
    @Override
    public String value() {
        return (forenames.isEmpty() ? "" : JOIN_SPACES.join(forenames) + ' ')
                + surname;
    }

    @Nonnull
    @Override
    public PersonProto.Name.Builder toProtoBuilder() {
        return Name.super.toProtoBuilder()
                .setPrefix(MoreObjects.firstNonNull(prefix, ""))
                .addAllForename(forenames)
                .setSurname(surname)
                .setSuffix(MoreObjects.firstNonNull(suffix, ""));
    }
}
