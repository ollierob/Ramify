package net.ramify.strategy.merge.address;

import net.ramify.model.place.address.Address;

import javax.annotation.Nonnull;
import java.util.Optional;

class ReturnUnknownAddress implements AddressMerge {

    @Nonnull
    @Override
    public Optional<Address> merge(@Nonnull Address t1, @Nonnull Address t2) {
        return Optional.of(Address.UNKNOWN);
    }

}
