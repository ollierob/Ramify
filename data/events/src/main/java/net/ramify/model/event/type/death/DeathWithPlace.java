package net.ramify.model.event.type.death;

import net.ramify.model.event.type.Death;
import net.ramify.model.event.type.misc.EventWithPlace;
import net.ramify.model.place.Place;

public class DeathWithPlace extends EventWithPlace<Death> implements Death {

    public DeathWithPlace(final Death delegate, final Place place) {
        super(delegate, place);
    }

}
