package net.ramify.strategy.merge.person.name;

import net.ramify.model.person.name.Name;

import javax.annotation.Nonnull;
import java.util.Optional;

class ReturnUnknownName implements NameMerge {

    @Nonnull
    @Override
    public Optional<Name> merge(@Nonnull Name t1, @Nonnull Name t2) {
        return Optional.of(Name.UNKNOWN);
    }

}
