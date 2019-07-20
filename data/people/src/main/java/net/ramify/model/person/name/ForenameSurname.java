package net.ramify.model.person.name;

import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
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
        this(null, forename == null || forename.isBlank() ? Collections.emptyList() : Collections.singletonList(forename), surname, null);
    }

    public ForenameSurname(final String prefix, final List<String> forenames, final String surname, final String suffix) {
        this.prefix = prefix;
        this.forenames = forenames;
        this.surname = MoreObjects.firstNonNull(surname, "");
        this.suffix = suffix;
    }

    @Nonnull
    public List<Character> initials(final boolean includeSurname) {
        final var initials = Lists.<Character>newArrayList();
        forenames.forEach(f -> initials.add(Character.toUpperCase(f.charAt(0))));
        if (includeSurname && !surname.isEmpty()) initials.add(surname.charAt(0));
        return initials;
    }

    @Nonnull
    public String surname() {
        return surname;
    }

    @Nonnull
    @Override
    public String value() {
        return ((forenames.isEmpty() ? "" : JOIN_SPACES.join(forenames) + ' ')
                + surname).trim();
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

    @Override
    public String toString() {
        return this.value();
    }
}
