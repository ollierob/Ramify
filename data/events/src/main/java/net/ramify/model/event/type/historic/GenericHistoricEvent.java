package net.ramify.model.event.type.historic;

import com.google.common.base.Preconditions;
import net.ramify.model.date.DateRange;
import net.ramify.model.event.EventId;
import net.ramify.model.event.historic.HistoricEvent;
import net.ramify.model.place.Place;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Objects;

import static net.ramify.utils.StringUtils.isBlank;

public class GenericHistoricEvent implements HistoricEvent {

    private final EventId id;
    private final DateRange date;
    private final Place place;
    private final String title;
    private final String description;

    public GenericHistoricEvent(EventId id, DateRange date, Place place, String title, final String description) {
        Preconditions.checkArgument(!isBlank(title), "Blank title");
        this.id = Objects.requireNonNull(id);
        this.date = Objects.requireNonNull(date);
        this.place = Objects.requireNonNull(place);
        this.title = Objects.requireNonNull(title);
        this.description = description;
    }

    @Nonnull
    @Override
    public EventId eventId() {
        return id;
    }

    @Nonnull
    @Override
    public String title() {
        return title;
    }

    @Nonnull
    @Override
    public DateRange date() {
        return date;
    }

    @Nonnull
    @Override
    public Place place() {
        return place;
    }

    @CheckForNull
    @Override
    public String description() {
        return description;
    }

}