package net.ramify.strategy.merge.person.name;

import net.ramify.model.person.name.Name;

import javax.annotation.Nonnull;
import java.util.Optional;

class EitherKnownName implements NameMerge {

    private final NameMerge delegate;

    EitherKnownName(NameMerge delegate) {
        this.delegate = delegate;
    }

    @Nonnull
    @Override
    public Optional<Name> merge(@Nonnull Name n1, @Nonnull Name n2) {
        if (n1.isUnknown()) {
            return Optional.of(n2);
        } else if (n2.isUnknown()) {
            return Optional.of(n1);
        } else {
            return delegate.merge(n1, n2);
        }
    }

}
