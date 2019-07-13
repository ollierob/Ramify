package net.ramify.model.person.name;

import javax.annotation.Nonnull;

public interface NameParser {

    @Nonnull
    Name parse(@Nonnull String name);

}
