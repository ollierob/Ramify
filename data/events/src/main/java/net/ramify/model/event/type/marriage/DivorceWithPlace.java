package net.ramify.model.event.type.marriage;

import net.ramify.model.event.type.misc.EventWithPlace;
import net.ramify.model.place.Place;

public class DivorceWithPlace extends EventWithPlace<DivorceEvent> implements DivorceEvent {

    public DivorceWithPlace(final DivorceEvent delegate, final Place place) {
        super(delegate, place);
    }

}
