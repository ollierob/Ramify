package net.ramify.model.person.feature;

import javax.annotation.Nonnull;

public class EyeColour extends StringPersonFeature {

    public EyeColour(final String colour) {
        super(colour);
    }

    @Nonnull
    @Override
    public String name() {
        return "Eye colour";
    }

}
