package net.ramify.model.person.name;

import com.google.common.collect.ImmutableSet;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Set;

public class ChangedName implements Name {

    private final Name before;
    private final Name after;

    public ChangedName(@Nonnull final Name before, @Nonnull final Name after) {
        this.before = Objects.requireNonNull(before);
        this.after = Objects.requireNonNull(after);
    }

    public Name before() {
        return before;
    }

    public Name after() {
        return after;
    }

    @Nonnull
    @Override
    public String value() {
        return after.value();
    }

    @Nonnull
    @Override
    public Set<String> variations() {
        return ImmutableSet.<String>builder()
                .addAll(before.variations())
                .addAll(after.variations())
                .build();
    }

}
