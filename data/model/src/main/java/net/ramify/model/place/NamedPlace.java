package net.ramify.model.place;

import javax.annotation.Nonnull;

public abstract class NamedPlace implements Place {

    private final String name;

    protected NamedPlace(final String name) {
        this.name = name;
    }

    @Nonnull
    @Override
    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NamedPlace that = (NamedPlace) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return this.getClass().getSimpleName() + "(" + name + ")";
    }

}
