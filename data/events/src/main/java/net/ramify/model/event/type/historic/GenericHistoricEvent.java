package net.ramify.model.event.type.historic;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import net.ramify.model.date.DateRange;
import net.ramify.model.event.EventId;
import net.ramify.model.event.historic.HistoricEvent;
import net.ramify.model.place.type.SettlementOrRegion;
import net.ramify.model.util.Link;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static net.ramify.utils.StringUtils.isBlank;

public class GenericHistoricEvent implements HistoricEvent {

    private final EventId id;
    private final DateRange date;
    private final SettlementOrRegion region;
    private final String title;
    private final String description;
    private final Set<Link> links;

    public GenericHistoricEvent(
            final EventId id,
            final DateRange date,
            final SettlementOrRegion region,
            final String title,
            final String description,
            final Set<Link> links) {
        Preconditions.checkArgument(!isBlank(title), "Blank title");
        this.id = Objects.requireNonNull(id);
        this.date = Objects.requireNonNull(date);
        this.region = region;
        this.title = Objects.requireNonNull(title);
        this.description = description;
        this.links = MoreObjects.firstNonNull(links, ImmutableSet.of());
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
    public SettlementOrRegion region() {
        return region;
    }

    @Override
    public Optional<String> description() {
        return Optional.ofNullable(description);
    }

    @Nonnull
    @Override
    public Set<Link> links() {
        return links;
    }

}
