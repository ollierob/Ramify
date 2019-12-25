package net.ramify.model.person.name;

import com.google.common.collect.ImmutableSet;

import javax.annotation.Nonnull;
import java.util.Set;

public class TranscribedName implements Name {

    private final String original;
    private final Name assumed;

    public TranscribedName(final String original, final Name assumed) {
        this.original = original;
        this.assumed = assumed;
    }

    @Nonnull
    @Override
    public String value() {
        return assumed.value();
    }

    public String original() {
        return original;
    }

    @Nonnull
    public Name assumed() {
        return assumed;
    }

    @Override
    public boolean contains(final String name) {
        final var lc = name.toLowerCase();
        return assumed.contains(lc) || original.toLowerCase().contains(lc);
    }

    @Nonnull
    @Override
    public Set<String> variations() {
        return ImmutableSet.<String>builder()
                .add(original)
                .addAll(assumed.variations())
                .build();
    }

}
