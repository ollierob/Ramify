package net.ramify.model.event.type.misc;

import com.google.common.base.Preconditions;
import net.ramify.model.event.AbstractPersonEvent;
import net.ramify.model.event.EventId;
import net.ramify.model.event.EventProperties;
import net.ramify.model.event.type.LifeEvent;
import net.ramify.model.person.PersonId;
import net.ramify.model.place.Place;

import javax.annotation.Nonnull;

import static net.ramify.utils.StringUtils.isBlank;

public class GenericLifeEvent extends AbstractPersonEvent<GenericLifeEvent> implements LifeEvent {

    private final String title;

    public GenericLifeEvent(
            final String title,
            final EventId id,
            final PersonId personId,
            final EventProperties properties) {
        super(id, personId, properties);
        Preconditions.checkArgument(!isBlank(title), "Invalid title");
        this.title = title;
    }

    @Nonnull
    @Override
    public String title() {
        return title;
    }

    @Nonnull
    @Override
    public LifeEvent with(final Place place) {
        throw new UnsupportedOperationException(); //TODO
    }

}
