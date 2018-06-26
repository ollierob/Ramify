package net.ramify.strategy.merge.address;

import net.ramify.model.place.address.Address;

import javax.annotation.Nonnull;
import java.util.Optional;

class EitherKnownAddress implements AddressMerge {

    private final AddressMerge delegate;

    EitherKnownAddress(final AddressMerge delegate) {
        this.delegate = delegate;
    }

    @Nonnull
    @Override
    public Optional<Address> merge(@Nonnull final Address t1, @Nonnull final Address t2) {
        if (t1.isUnknown()) {
            return Optional.of(t2);
        } else if (t2.isUnknown()) {
            return Optional.of(t1);
        } else {
            return delegate.merge(t1, t2);
        }
    }
}
