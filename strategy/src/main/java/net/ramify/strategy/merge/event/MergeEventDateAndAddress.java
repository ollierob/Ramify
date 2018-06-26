package net.ramify.strategy.merge.event;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.Event;
import net.ramify.model.place.address.Address;
import net.ramify.model.place.address.HasAddress;
import net.ramify.strategy.merge.address.AddressMerge;
import net.ramify.strategy.merge.date.DateMerge;

import java.util.Optional;

abstract class MergeEventDateAndAddress<E extends Event & HasAddress> implements EventMerge<E> {

    private final DateMerge dateMerge;
    private final AddressMerge addressMerge;

    MergeEventDateAndAddress(final DateMerge dateMerge, final AddressMerge addressMerge) {
        this.dateMerge = dateMerge;
        this.addressMerge = addressMerge;
    }

    @Override
    public Optional<E> merge(E e1, E e2) {
        final Optional<DateRange> date = dateMerge.merge(e1.date(), e2.date());
        if (!date.isPresent()) {
            return Optional.empty();
        }
        final Optional<Address> address = addressMerge.merge(e1.address(), e2.address());
        if (!address.isPresent()) {
            return Optional.empty();
        }
        return Optional.of(this.of(date.get(), address.get()));
    }

    protected abstract E of(DateRange date, Address address);

}
