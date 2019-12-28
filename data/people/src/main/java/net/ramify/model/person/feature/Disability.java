package net.ramify.model.person.feature;

import javax.annotation.Nonnull;

public class Disability extends StringPersonFeature {

    public Disability(String value) {
        super(value);
    }

    @Nonnull
    @Override
    public String name() {
        return "Disability";
    }

}
