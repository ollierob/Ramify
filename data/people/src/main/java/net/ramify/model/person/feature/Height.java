package net.ramify.model.person.feature;

import javax.annotation.Nonnull;

public class Height extends AbstractPersonFeature {

    private final int heightCm;

    public Height(final int heightCm) {
        this.heightCm = heightCm;
    }

    @Nonnull
    @Override
    public String name() {
        return "Height";
    }

    public int heightCm() {
        return heightCm;
    }

    public double heightM() {
        return heightCm / 100.d;
    }

    @Nonnull
    @Override
    public String value() {
        return this.heightM() + "m";
    }

}
