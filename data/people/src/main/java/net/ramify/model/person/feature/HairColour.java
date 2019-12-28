package net.ramify.model.person.feature;

import javax.annotation.Nonnull;

public class HairColour extends StringPersonFeature {

    public HairColour(final String value) {
        super(value);
    }

    @Nonnull
    @Override
    public String name() {
        return "Hair colour";
    }

}
