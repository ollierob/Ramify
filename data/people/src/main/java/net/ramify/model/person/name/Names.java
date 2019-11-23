package net.ramify.model.person.name;

import com.google.common.collect.ImmutableSet;

import javax.annotation.Nonnull;
import java.util.Set;

public class Names implements Name {

    public static Name of(final Name main, final Name... others) {
        if (others == null || others.length == 0) return main;
        return new Names(main, ImmutableSet.copyOf(others));
    }

    private final Name main;
    private final Set<Name> others;

    Names(final Name main, final Set<Name> others) {
        this.main = main;
        this.others = others;
    }

    @Nonnull
    @Override
    public String value() {
        return main.value();
    }

    @Nonnull
    @Override
    public Set<String> variations() {
        final var builder = ImmutableSet.<String>builder().addAll(main.variations());
        others.forEach(other -> builder.addAll(other.variations()));
        return builder.build();
    }

}
