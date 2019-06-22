package net.ramify.model;

import javax.annotation.Nonnull;
import java.util.Objects;

public abstract class Id {

    private final String value;

    protected Id(final String value) {
        this.value = Objects.requireNonNull(value);
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
        if (o == null || getClass() != o.getClass()) return false;
        Id id = (Id) o;
        return value.equals(id.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
