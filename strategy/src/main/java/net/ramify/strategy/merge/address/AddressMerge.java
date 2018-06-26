package net.ramify.strategy.merge.address;

import net.ramify.model.place.address.Address;
import net.ramify.strategy.merge.Merge;

public interface AddressMerge extends Merge<Address> {

    AddressMerge DEFAULT = new EitherKnownAddress(new ReturnUnknownAddress());

}
