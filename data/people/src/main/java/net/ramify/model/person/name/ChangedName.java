package net.ramify.model.person.name;

import com.google.common.collect.ImmutableSet;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Set;

public record ChangedName(Name before, Name after) implements Name {

    public ChangedName {
        Objects.requireNonNull(before, "before");
        Objects.requireNonNull(after, "after");
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
