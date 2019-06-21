package net.ramify.model;

import javax.annotation.Nonnull;

public abstract class Id {

    private final String value;

    protected Id(final String value) {
        this.value = value;
    }

    @Nonnull
    public String value() {
        return value;
    }

}
