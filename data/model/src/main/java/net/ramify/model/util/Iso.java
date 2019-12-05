package net.ramify.model.util;

import javax.annotation.Nonnull;
import java.util.Objects;

public abstract class Iso {

    private final String value;

    protected Iso(@Nonnull final String value) {
        this.value = Objects.requireNonNull(value);
    }

    @Nonnull
    public String value() {
        return value;
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof Iso
                && this.value.equals(((Iso) obj).value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return this.value();
    }

}
