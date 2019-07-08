package net.ramify.model.record;

import javax.annotation.Nonnull;

public interface HasTitleDescription {

    @Nonnull
    String title();

    @Nonnull
    String description();

}
