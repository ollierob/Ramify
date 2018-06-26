package net.ramify.model.person;

import javax.annotation.Nonnull;
import java.util.Collection;

public interface PersonalAttributeHistory {

    @Nonnull
    Collection<PersonalAttributes> attributes();

}
