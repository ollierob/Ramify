package net.ramify.strategy.merge.event;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.Death;
import net.ramify.model.place.address.Address;
import net.ramify.strategy.merge.address.AddressMerge;
import net.ramify.strategy.merge.date.DateMerge;

public class DeathMerge extends MergeEventDateAndAddress<Death> {

    public DeathMerge(final DateMerge dateMerge, final AddressMerge addressMerge) {
        super(dateMerge, addressMerge);
    }

    @Override
    protected Death of(DateRange date, Address address) {
        return new Death(date, address);
    }

}
