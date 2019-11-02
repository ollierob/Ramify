package net.ramify.model.event.type.birth;

import net.ramify.model.event.type.misc.EventWithPlace;
import net.ramify.model.place.Place;

public class BaptismWithPlace extends EventWithPlace<Baptism> implements Baptism {

    public BaptismWithPlace(final Baptism delegate, final Place place) {
        super(delegate, place);
    }

}
