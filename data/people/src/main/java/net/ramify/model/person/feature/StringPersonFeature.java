package net.ramify.model.person.feature;

import javax.annotation.Nonnull;

abstract class StringPersonFeature extends AbstractPersonFeature {

    private final String value;

    StringPersonFeature(String value) {
        this.value = value;
    }

    @Nonnull
    @Override
    public String value() {
        return value;
    }

}
