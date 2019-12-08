package net.ramify.model.event.type.death;

import net.ramify.model.event.type.DeathEvent;
import net.ramify.model.event.type.misc.EventWithPlace;
import net.ramify.model.place.Place;

public class DeathWithPlace extends EventWithPlace<DeathEvent> implements DeathEvent {

    public DeathWithPlace(final DeathEvent delegate, final Place place) {
        super(delegate, place);
    }

}
