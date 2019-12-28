package net.ramify.model.person.feature;

import javax.annotation.Nonnull;

public class Literacy extends AbstractPersonFeature {

    private final boolean literate;

    public Literacy(final boolean literate) {
        this.literate = literate;
    }

    @Nonnull
    @Override
    public String name() {
        return "Literacy";
    }

    public boolean isLiterate() {
        return literate;
    }

    @Nonnull
    @Override
    public String value() {
        return String.valueOf(literate);
    }

}
