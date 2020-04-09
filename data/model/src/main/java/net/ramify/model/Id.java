package net.ramify.model;

import com.google.common.base.Preconditions;

import javax.annotation.Nonnull;
import java.util.Objects;

public abstract class Id {

    private final String value;

    protected Id(@Nonnull final String value) {
        this.value = Objects.requireNonNull(value, "Null value");
        Preconditions.checkArgument(!value.isBlank(), "Blank value");
    }

    @Nonnull
    public String value() {
        return value;
    }

    public boolean isUnknown() {
        return value.isBlank() || value.equals("?");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        return o instanceof Id id
                && this.equals(id);
    }

    protected boolean equals(@Nonnull final Id that) {
        return this.getClass() == that.getClass()
                & Objects.equals(this.value, that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + '[' + value + ']';
    }

}
