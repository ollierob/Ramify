package net.ramify.model.person.name;

import com.google.common.collect.ImmutableSet;

import javax.annotation.Nonnull;
import java.util.Set;

public record TranscribedName(String original, Name assumed) implements Name {

    @Nonnull
    @Override
    public String value() {
        return assumed.value();
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
