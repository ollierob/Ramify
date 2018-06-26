package net.ramify.strategy.merge.event;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.Birth;
import net.ramify.model.place.address.Address;
import net.ramify.strategy.merge.address.AddressMerge;
import net.ramify.strategy.merge.date.DateMerge;

public class BirthMerge extends MergeEventDateAndAddress<Birth> {

    public BirthMerge(final DateMerge dateMerge, final AddressMerge addressMerge) {
        super(dateMerge, addressMerge);
    }

    @Override
    protected Birth of(final DateRange date, final Address address) {
        return new Birth(date, address);
    }

}
