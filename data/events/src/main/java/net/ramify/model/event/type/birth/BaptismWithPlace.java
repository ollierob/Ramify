package net.ramify.model.event.type.birth;

import net.ramify.model.event.type.misc.EventWithPlace;
import net.ramify.model.place.Place;

public class BaptismWithPlace extends EventWithPlace<BaptismEvent> implements BaptismEvent {

    public BaptismWithPlace(final BaptismEvent delegate, final Place place) {
        super(delegate, place);
    }

}
