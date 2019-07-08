package net.ramify.model.record;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

public interface HasTitleDescription {

    @Nonnull
    String title();

    @CheckForNull
    String description();

}
