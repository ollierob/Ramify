package net.ramify.model.person.name;

import com.google.common.base.Preconditions;

import javax.annotation.Nonnull;

public class StringName implements Name {

    private final String value;

    public StringName(final String value) {
        Preconditions.checkArgument(value != null && !value.isBlank(), "Empty name");
        this.value = value;
    }

    @Nonnull
    @Override
    public String value() {
        return value;
    }

}
